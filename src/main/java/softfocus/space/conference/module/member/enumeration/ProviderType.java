package softfocus.space.conference.module.member.enumeration;

public enum ProviderType {
    KAKAO("KAKAO","카카오"),
    GOOGLE("GOOGLE","구글"),
    NAVER("NAVER","네이버");

    private final String key;
    private final String value;

    ProviderType(String key,String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
