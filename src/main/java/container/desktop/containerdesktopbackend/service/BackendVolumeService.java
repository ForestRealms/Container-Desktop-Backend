package container.desktop.containerdesktopbackend.service;

import com.github.dockerjava.api.DockerClient;
import container.desktop.api.entity.Volume;
import container.desktop.api.exception.UpdatingException;
import container.desktop.api.repository.VolumeRepository;
import container.desktop.api.service.VolumeService;
import container.desktop.containerdesktopbackend.entity.BackendVolume;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("volume_service")
public class BackendVolumeService implements VolumeService<BackendVolume> {

    private final VolumeRepository<BackendVolume> volumeRepository;
    private final DockerClient client;

    public BackendVolumeService(VolumeRepository<BackendVolume> volumeRepository, DockerClient client) {
        this.volumeRepository = volumeRepository;
        this.client = client;
    }


    @Override
    public List<? extends Volume> list() {
        return volumeRepository.findAll();
    }

    @Nullable
    @Override
    public Volume findById(String id) {
        return volumeRepository.findById(id).orElse(null);
    }

    @Override
    public List<? extends Volume> findByIds(List<String> ids) {
        return volumeRepository.findByIdIn(ids);
    }

    @Override
    public void deleteById(String id) {
        Optional<BackendVolume> optional = volumeRepository.findById(id);
        assert optional.isPresent();
        volumeRepository.delete(optional.get());
    }

    @Override
    public String create(Integer size) {
        return create(size, null);
    }

    @Override
    public String create(Integer size, String customName) {
        String name = client.createVolumeCmd()
                .withDriver("loopback")
                .withDriverOpts(Map.of(
                        "size", size + "g",
                        "sparse", "true",
                        "fs", "ext4"
                ))
                .exec().getName();
        volumeRepository.saveAndFlush(BackendVolume.builder()
                .customName(customName)
                        .id(name)
                        .size(size)
                .build());
        return name;
    }

    @Override
    public void update(BackendVolume entity) throws UpdatingException {
        volumeRepository.saveAndFlush(entity);
    }
}