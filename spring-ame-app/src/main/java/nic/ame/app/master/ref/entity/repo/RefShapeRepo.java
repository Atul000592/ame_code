package nic.ame.app.master.ref.entity.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nic.ame.app.master.ref.entity.CategoryShapeMaster;

public interface RefShapeRepo  extends JpaRepository<CategoryShapeMaster, Integer>{

	List<CategoryShapeMaster> findByTypeCode(String shapeCategory);

}
