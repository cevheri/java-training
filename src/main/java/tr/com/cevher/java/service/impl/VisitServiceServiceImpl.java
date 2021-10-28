package tr.com.cevher.java.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.com.cevher.java.domain.VisitService;
import tr.com.cevher.java.repository.VisitServiceRepository;
import tr.com.cevher.java.service.VisitServiceService;
import tr.com.cevher.java.service.dto.VisitServiceDTO;
import tr.com.cevher.java.service.mapper.VisitServiceMapper;

/**
 * Service Implementation for managing {@link VisitService}.
 */
@Service
@Transactional
public class VisitServiceServiceImpl implements VisitServiceService {

    private final Logger log = LoggerFactory.getLogger(VisitServiceServiceImpl.class);

    private final VisitServiceRepository visitServiceRepository;

    private final VisitServiceMapper visitServiceMapper;

    public VisitServiceServiceImpl(VisitServiceRepository visitServiceRepository, VisitServiceMapper visitServiceMapper) {
        this.visitServiceRepository = visitServiceRepository;
        this.visitServiceMapper = visitServiceMapper;
    }

    @Override
    public VisitServiceDTO save(VisitServiceDTO visitServiceDTO) {
        log.debug("Request to save VisitService : {}", visitServiceDTO);
        VisitService visitService = visitServiceMapper.toEntity(visitServiceDTO);
        visitService = visitServiceRepository.save(visitService);
        return visitServiceMapper.toDto(visitService);
    }

    @Override
    public Optional<VisitServiceDTO> partialUpdate(VisitServiceDTO visitServiceDTO) {
        log.debug("Request to partially update VisitService : {}", visitServiceDTO);

        return visitServiceRepository
            .findById(visitServiceDTO.getId())
            .map(existingVisitService -> {
                visitServiceMapper.partialUpdate(existingVisitService, visitServiceDTO);

                return existingVisitService;
            })
            .map(visitServiceRepository::save)
            .map(visitServiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VisitServiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VisitServices");
        return visitServiceRepository.findAll(pageable).map(visitServiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VisitServiceDTO> findOne(Long id) {
        log.debug("Request to get VisitService : {}", id);
        return visitServiceRepository.findById(id).map(visitServiceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VisitService : {}", id);
        visitServiceRepository.deleteById(id);
    }
}
