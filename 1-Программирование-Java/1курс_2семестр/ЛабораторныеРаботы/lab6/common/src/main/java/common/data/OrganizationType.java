package common.data;

import java.io.Serializable;

/**
 * Enumeration with organization types.
 */
public enum OrganizationType implements Serializable {
    COMMERCIAL,
    GOVERNMENT,
    PRIVATE_LIMITED_COMPANY,
    OPEN_JOINT_STOCK_COMPANY;

    
    /**
     * This function returns a comma separated list of the names of all the values in the enum
     * 
     * @return The types of the organization.
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (OrganizationType category : values()) {
            nameList.append(category.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
