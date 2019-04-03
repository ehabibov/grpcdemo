import org.apache.commons.io.FileUtils;
import org.nuxeo.onedrive.client.*;

public class Entity {

    public static String toString(OneDriveItem.Metadata meta){
        String name = meta.getName();
        String description = meta.getDescription();
        String size = FileUtils.byteCountToDisplaySize(meta.getSize());
        String link = meta.getWebUrl();

        String entity = null;
        if (meta.isFile()){
            entity = "fi";
        } else if (meta.isFolder()){
            entity = "fo";
        }

        String createdTime = meta.getCreatedDateTime().toLocalDateTime().toString();
        String createdBy = meta.getCreatedBy().getUser().getDisplayName();
        String lastModifiedTime = meta.getLastModifiedDateTime().toLocalDateTime().toString();
        String lastModifiedBy = meta.getLastModifiedBy().getUser().getDisplayName();

        String parent = meta.getParentReference().getPath();


        return String.format(
                "Entity={%s}, name={%s}, description={%s}, size={%s}, link={%s}, createdBy={%s}, createdTime={%s}, " +
                        "modifiedBy={%s}, modifiedTime={%s}, parentDir={%s}",
                entity, name, description,size, link, createdBy, createdTime, lastModifiedBy, lastModifiedTime, parent
        );
    }

}
