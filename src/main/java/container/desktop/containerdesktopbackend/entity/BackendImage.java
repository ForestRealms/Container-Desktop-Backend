package container.desktop.containerdesktopbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import container.desktop.api.entity.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
public class BackendImage implements Image {
    @Id
    @Column(name = "id")
    private String id;
    private String name;
    @Column(name = "min_vcpu")
    @JsonProperty("min_vcpus")
    private Integer minimumVcpus;
    @JsonProperty("min_ram")
    @Column(name = "min_ram")
    private Integer minimumRAM;
    @Column(name = "remote_desktop_port")
    @JsonProperty("remote_desktop_port")
    private Integer remoteDesktopPort;
    @Column(name = "available")
    private boolean available;
    @Column(name = "is_public")
    @JsonIgnore
    private boolean _public;

    private transient final Map<String, Object> attributes = new HashMap<>();

    @Override
    public void addAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public void deleteAttribute(String key) {
        attributes.remove(key);
    }

    @Override
    public void clearAttributes() {
        attributes.clear();
    }

    @Override
    public boolean isPublic() {
        return _public;
    }
}
