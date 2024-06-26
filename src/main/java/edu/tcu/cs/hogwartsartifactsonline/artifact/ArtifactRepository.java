package edu.tcu.cs.hogwartsartifactsonline.artifact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository // this annotation is optional
public interface ArtifactRepository extends JpaRepository<Artifact, String>, JpaSpecificationExecutor<Artifact> {
}
