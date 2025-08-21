package com.adhunikkethi.adhunnikkethi.Respository;

import com.adhunikkethi.adhunnikkethi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
