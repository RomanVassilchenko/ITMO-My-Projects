package collections;

/**
 * Enumeration of organization type
 */
public enum OrganizationType {
    COMMERCIAL,
    GOVERNMENT,
    PRIVATE_LIMITED_COMPANY,
    OPEN_JOINT_STOCK_COMPANY;

    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values divided by comma.
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (OrganizationType category : values()) {
            nameList.append(category.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
