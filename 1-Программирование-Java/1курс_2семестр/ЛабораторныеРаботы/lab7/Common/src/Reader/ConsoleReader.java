package Reader;

import java.util.Scanner;

public class ConsoleReader {
    public static Object conditionalRead(Scanner scanner, String phrase, boolean canNull, Caster caster, Condition... conditions) {
        boolean checkedConditions;
        String next;
        while (true) {
            System.out.print(phrase);
            checkedConditions = true;
            next = scanner.nextLine().trim();
            if (canNull && next.equals("")) {
                return null;
            } else {
                for (Condition cond : conditions) {
                    try {
                        if (next.equals("")) {
                            checkedConditions = false;
                            if (!phrase.equals("")) System.out.println("Error :You entered the data incorrectly.");
                            break;
                        }
                        if (!cond.check(next)) {
                            checkedConditions = false;
                            if (!phrase.equals("")) System.out.println("Error :You entered the data incorrectly.");
                            break;
                        }
                    } catch (Exception e) {
                        if (!phrase.equals("")) System.out.println("Error :You entered the data incorrectly.");
                        checkedConditions = false;
                    }

                    if (checkedConditions) {
                        return caster.cast(next);
                    }
                }
                if (checkedConditions) {
                    return caster.cast(next);
                }
            }

        }
    }
}
