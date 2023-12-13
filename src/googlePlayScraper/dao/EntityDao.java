package googlePlayScraper.dao;

import java.util.List;

public interface EntityDao<Entity, No> {

	public void persist(Entity entity);

	public List<Entity> showAll();

}
