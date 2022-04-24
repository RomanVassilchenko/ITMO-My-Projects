package Data;

import java.io.Serializable;
import java.util.Scanner;

public enum OrganizationType implements Serializable {
    COMMERCIAL,
    GOVERNMENT,
    PRIVATE_LIMITED_COMPANY,
    OPEN_JOINT_STOCK_COMPANY;

    static OrganizationType fillOrganizationType(Scanner scanner) {
        while (true) {
            System.out.println("Enter one of the values: COMMERCIAL, GOVERNMENT, PRIVATE_LIMITED_COMPANY, OPEN_JOINT_STOCK_COMPANY");
            String organizationType = scanner.nextLine().trim();
            if (organizationType.equalsIgnoreCase("COMMERCIAL")) {
                return COMMERCIAL;
            } else if (organizationType.equalsIgnoreCase("GOVERNMENT")) {
                return GOVERNMENT;
            } else if (organizationType.equalsIgnoreCase("PRIVATE_LIMITED_COMPANY")) {
                return PRIVATE_LIMITED_COMPANY;
            } else if (organizationType.equalsIgnoreCase("OPEN_JOINT_STOCK_COMPANY")) {
                return OPEN_JOINT_STOCK_COMPANY;
            } else {
                System.err.println("You have entered a non-existent value.");
            }

        }
    }

    static OrganizationType fillOrganizationTypeFromFile(Scanner scanner) {
        while (true) {
            String organizationType = scanner.nextLine().trim();
            if (organizationType.equalsIgnoreCase("COMMERCIAL")) {
                return COMMERCIAL;
            } else if (organizationType.equalsIgnoreCase("GOVERNMENT")) {
                return GOVERNMENT;
            } else if (organizationType.equalsIgnoreCase("PRIVATE_LIMITED_COMPANY")) {
                return PRIVATE_LIMITED_COMPANY;
            } else if (organizationType.equalsIgnoreCase("OPEN_JOINT_STOCK_COMPANY")) {
                return OPEN_JOINT_STOCK_COMPANY;
            } else {
                System.err.println("You have entered a non-existent value.");
            }

        }
    }


}
