package com.example.ecommercebooksales.service;

import com.example.ecommercebooksales.dto.PublisherDTO;
import com.example.ecommercebooksales.entity.Publisher;
import com.example.ecommercebooksales.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    // ---------------- CREATE ----------------
    public PublisherDTO createPublisher(PublisherDTO request) {
        Publisher publisher = new Publisher();
        publisher.setPublisherName(request.getPublisher_name());
        publisher.setAddress(request.getAddress());
        publisher.setPhone(request.getPhone());
        publisher.setEmail(request.getEmail());
        publisher.setWebsite(request.getWebsite());
        publisher.setCountry(request.getCountry());
        publisher.setFoundedYear(request.getFounded_year());
        publisher.setDescription(request.getDescription());

        Publisher saved = publisherRepository.save(publisher);
        return convertToDTO(saved);
    }

    // ---------------- READ ----------------
    public List<PublisherDTO> getAllPublisher() {
        return publisherRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    // ---------------- FIND BY ID ----------------
    public PublisherDTO getPublisherById(Long id) {
        return publisherRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // ---------------- UPDATE ----------------
    public PublisherDTO updatePublisher(Long id, PublisherDTO request) {
        return publisherRepository.findById(id)
                .map(publisher ->{
                    publisher.setPublisherName(request.getPublisher_name());
                    publisher.setAddress(request.getAddress());
                    publisher.setPhone(request.getPhone());
                    publisher.setEmail(request.getEmail());
                    publisher.setWebsite(request.getWebsite());
                    publisher.setCountry(request.getCountry());
                    publisher.setFoundedYear(request.getFounded_year());
                    publisher.setDescription(request.getDescription());

                    Publisher updated = publisherRepository.save(publisher);
                    return convertToDTO(updated);
                }).orElse(null);
    }

    // ---------------- DELETE ----------------
    public boolean deletePublisher(Long id) {
        if (!publisherRepository.existsById(id)) return false;
        publisherRepository.deleteById(id);
        return true;
    }

    // ---------------- CONVERT ----------------
    private PublisherDTO convertToDTO(Publisher publisher){
        PublisherDTO dto = new PublisherDTO();
        dto.setPublisher_id(publisher.getPublisherId());
        dto.setPublisher_name(publisher.getPublisherName());
        dto.setAddress(publisher.getAddress());
        dto.setPhone(publisher.getPhone());
        dto.setEmail(publisher.getEmail());
        dto.setWebsite(publisher.getWebsite());
        dto.setCountry(publisher.getCountry());
        dto.setFounded_year(publisher.getFoundedYear());
        dto.setDescription(publisher.getDescription());
        return dto;
    }
}
