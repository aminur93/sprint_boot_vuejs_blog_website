package aminurdev.com.backend.domain.repository;

import aminurdev.com.backend.domain.entity.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactUsRepository extends JpaRepository<ContactUs, Integer> {
}
