package io.infectnet.server.workbench;

import io.infectnet.server.engine.Engine;
import io.infectnet.server.engine.core.player.Player;
import io.infectnet.server.engine.core.script.generation.ScriptGenerationFailedException;

import java.util.Optional;
import java.util.Scanner;

public class Hephaestus {
  public static void main(String[] args) {
    final Scanner scanner = new Scanner(System.in);

    String username = readUsername(scanner);

    String sourceCode = readSourceCode(scanner);

    startEngine(username, sourceCode);
  }

  private static void startEngine(String username, String sourceCode) {
    final Engine engine = Engine.create();

    Optional<Player> playerOptional = engine.registerPlayer(username);

    if (!playerOptional.isPresent()) {
      System.err.println("Could not create player");

      return;
    }

    try {
      engine.addCode(playerOptional.get(), sourceCode);

      engine.addEntityTo(playerOptional.get());

      engine.start();

      System.out.println("Now running...");
    } catch (ScriptGenerationFailedException e) {
      System.err.println(e);
    }
  }

  private static String readUsername(Scanner scanner) {
    return scanner.nextLine();
  }

  private static String readSourceCode(Scanner scanner) {
    String line;

    StringBuilder codeBuilder = new StringBuilder();

    do {
      line = scanner.nextLine();

      if (line.startsWith("#")) {
        break;
      }

      codeBuilder.append(line);

    } while(scanner.hasNextLine());

    return codeBuilder.toString();
  }
}
