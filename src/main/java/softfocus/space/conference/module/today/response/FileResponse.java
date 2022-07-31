package softfocus.space.conference.module.today.response;

import softfocus.space.conference.module.today.dto.FileGrapeDTO;

import java.util.List;

public class FileResponse {
    private List<FileGrapeDTO> data;
    public FileResponse(List<FileGrapeDTO> data) {
        this.data = data;
    }

    public List<FileGrapeDTO> getData() {
        return data;
    }

    public void setData(List<FileGrapeDTO> data) {
        this.data = data;
    }
}
