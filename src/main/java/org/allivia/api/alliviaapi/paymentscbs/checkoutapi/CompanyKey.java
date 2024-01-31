package org.allivia.api.alliviaapi.paymentscbs.checkoutapi;

public class CompanyKey {
    private String accessKey;
    private String profileId;

    public CompanyKey(String accessKey, String profileId) {
        this.accessKey = accessKey;
        this.profileId = profileId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    

}
