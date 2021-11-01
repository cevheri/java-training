package tr.com.cevher.java.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.cevher.java.domain.SystemSetup;
import tr.com.cevher.java.repository.SystemSetupRepository;
import tr.com.cevher.java.service.SystemSetupService;
import tr.com.cevher.java.service.dto.SystemSetupDTO;
import tr.com.cevher.java.service.mapper.SystemSetupMapper;

/**
 * Service Implementation for managing {@link SystemSetup}.
 */
@Service
@Transactional
public class SystemSetupServiceImpl implements SystemSetupService {

    private final Logger log = LoggerFactory.getLogger(SystemSetupServiceImpl.class);

    private final SystemSetupRepository systemSetupRepository;

    private final SystemSetupMapper systemSetupMapper;

    public SystemSetupServiceImpl(SystemSetupRepository systemSetupRepository, SystemSetupMapper systemSetupMapper) {
        this.systemSetupRepository = systemSetupRepository;
        this.systemSetupMapper = systemSetupMapper;
    }

    @Override
    public SystemSetupDTO save(SystemSetupDTO systemSetupDTO) {
        log.debug("Request to save SystemSetup : {}", systemSetupDTO);
        SystemSetup systemSetup = systemSetupMapper.toEntity(systemSetupDTO);
        systemSetup = systemSetupRepository.save(systemSetup);
        return systemSetupMapper.toDto(systemSetup);
    }

    @Override
    public Optional<SystemSetupDTO> partialUpdate(SystemSetupDTO systemSetupDTO) {
        log.debug("Request to partially update SystemSetup : {}", systemSetupDTO);

        return systemSetupRepository
            .findById(systemSetupDTO.getId())
            .map(existingSystemSetup -> {
                systemSetupMapper.partialUpdate(existingSystemSetup, systemSetupDTO);

                return existingSystemSetup;
            })
            .map(systemSetupRepository::save)
            .map(systemSetupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SystemSetupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemSetups");
        return systemSetupRepository.findAll(pageable).map(systemSetupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SystemSetupDTO> findOne(Long id) {
        log.debug("Request to get SystemSetup : {}", id);
        return systemSetupRepository.findById(id).map(systemSetupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SystemSetup : {}", id);
        systemSetupRepository.deleteById(id);
    }
}
