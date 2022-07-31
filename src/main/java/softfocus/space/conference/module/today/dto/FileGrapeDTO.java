package softfocus.space.conference.module.today.dto;

public class FileGrapeDTO {

    private String name;
    private String type;
    private String src;
    private Integer height;
    private Integer width;

    public FileGrapeDTO(String name, String type, String src, Integer height, Integer width) {
        this.name = name;
        this.type = type;
        this.src = src;
        this.height = height;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

}
