package managers;

import collections.Organization;
import collections.OrganizationType;

import java.util.Iterator;
import java.util.List;

public class Validator {
    List<Organization> organization;

    public Validator(List<Organization> organization) {
        this.organization = organization;
    }

    public List<Organization> validate(){
        for(Iterator<Organization> iterator = organization.iterator(); iterator.hasNext(); ){
            Organization org = iterator.next();
            if(org.getId() <= 0) {iterator.remove(); continue;}
            if(org.getName() == null || org.getName().equals("")) {iterator.remove(); continue;}
            if(org.getCoordinates() == null) {iterator.remove(); continue;}
            if(org.getCreationDate() == null) {iterator.remove(); continue;}
            if(org.getAnnualTurnover() <= 0) {iterator.remove(); continue;}
            if(org.getEmployeesCount() <= 0) {iterator.remove(); continue;}

            if(org.getPostalAddress() != null){
                if(org.getCoordinates().getY() == null) {iterator.remove(); continue;}
            }
            if(org.getPostalAddress() != null){
                if(org.getPostalAddress().getZipCode().length() > 22) {iterator.remove(); continue;}
            }

        }
        return organization;
    }

}
