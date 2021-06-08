package org.sid.dao;

import org.sid.entities.Widget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WidgateRepository extends JpaRepository<Widget,Long> {
}
