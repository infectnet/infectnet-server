package io.infectnet.server.service.impl

import io.infectnet.server.service.Converter
import io.infectnet.server.service.exception.MappingAlreadyReservedException
import spock.lang.Specification

class ConverterServiceImplTest extends Specification {

    def final TEST_USERNAME = "test"

    ConverterServiceImpl converterService

    class User {
        String name

        User(String value) {
            this.name = value
        }
    }

    class UserDTO {
        String name

        UserDTO(String value) {
            this.name = value
        }
    }

    def setup() {
        converterService = Spy(ConverterServiceImpl)
    }

    def "new converter mapping can be added"() {
        given: "a new converter mapping"
            Converter<User, UserDTO> converterMapping = Mock(Converter)
            converterMapping.convert(_) >> new UserDTO(TEST_USERNAME)
            _ * converterMapping.getSourceClass() >> User
            _ * converterMapping.getTargetClass() >> UserDTO

        when: "the new converter mapping is added"
            converterService.addConverterMapping(converterMapping)

        then: "the service should have the converter mapping"
            converterService.getConverterMapping(User, UserDTO).get() == converterMapping

    }

    def "new colliding converter mapping can't be added"() {
        given: "a converter mapping in the service"
            Converter<User, UserDTO> converterMapping = Mock(Converter)
            converterMapping.convert(_) >> new UserDTO(TEST_USERNAME)
            _ * converterMapping.getSourceClass() >> User
            _ * converterMapping.getTargetClass() >> UserDTO

            converterService.addConverterMapping(converterMapping)

        when: "we want to add the same converter mapping again"
            converterService.addConverterMapping(converterMapping)


        then: "MappingAlreadyReservedException is thrown"
            thrown(MappingAlreadyReservedException)

    }

    def "converter mapping can be used"() {
        given: "a converter mapping"
            def source = new User(TEST_USERNAME)

            Converter<User, UserDTO> converterMapping = Mock(Converter)
            _ * converterMapping.getSourceClass() >> User
            _ * converterMapping.getTargetClass() >> UserDTO
            1 * converterMapping.convert(source) >> new UserDTO(TEST_USERNAME)

            converterService.addConverterMapping(converterMapping)

            def expected = new UserDTO(TEST_USERNAME)

        when: "we convert an object to the destination class"
            def result = converterService.map(source, UserDTO)

        then: "we will get the converted object "
            result.name == expected.name
    }

    def "service should choose the correct converter mapping"() {
        given: "multiple converter mappings"
            Converter<User, UserDTO> userUserDTOConverter =
                    [
                            convert       : { User source -> new UserDTO(source.getName()) },
                            getSourceClass: { -> User },
                            getTargetClass: { -> UserDTO }
                    ] as Converter<User, UserDTO>

            Converter<UserDTO, User> userDTOUserConverter =
                    [
                            convert       : { UserDTO destination -> new User(destination.getName()) },
                            getSourceClass: { -> UserDTO },
                            getTargetClass: { -> User }
                    ] as Converter<UserDTO, User>

            converterService.addConverterMapping(userUserDTOConverter)
            converterService.addConverterMapping(userDTOUserConverter)

        when: ""
            def result = converterService.getConverterMapping(User, UserDTO)

        then: "we will get the appropriate converter mapping"
            result.get() == userUserDTOConverter


    }
}