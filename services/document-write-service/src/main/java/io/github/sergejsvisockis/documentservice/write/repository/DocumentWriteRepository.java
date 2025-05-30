package io.github.sergejsvisockis.documentservice.write.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentWriteRepository extends JpaRepository<DocumentMetadata, Long> {
}
