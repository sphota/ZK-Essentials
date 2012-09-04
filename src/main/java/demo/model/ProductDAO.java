package demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import demo.model.bean.Product;

/**
 * @author zkessentials store
 * 
 *         This class provides functionality to access the {@code Product} model
 *         storage system
 * 
 */
public class ProductDAO {

	private static final Map<Long, Product> dbModel = new HashMap<Long, Product>();

	private static final long SEC = 1000;
	private static final long MIN = 60 * SEC;
	private static final long HOUR = 60 * MIN;
	private static final long DAY = 24 * HOUR;

	private static final long time = System.currentTimeMillis();
	public static final Product PROD1 = new Product(1, "ZK Framework", new Date(time
			- 3 * DAY + 5 * HOUR + 30 * MIN), 999F, 30, true, "/image/zk_banner_image.png");
	public static final Product PROD2 = new Product(2, "ZK Spring", new Date(time
			- 5 * DAY + 3 * HOUR), 98.9F, 43, true, "/image/zkspring_banner_image.png");
	public static final Product PROD3 = new Product(3, "ZK Spreadsheet", new Date(
			time - 10 * DAY + 5 * HOUR + +33 * MIN), 389.98F, 12, true,
			"/image/zss_banner_image.png");
	public static final Product PROD4 = new Product(4, "ZK Pivottable", new Date(time
			- 7 * DAY + 8 * HOUR), 389.98F, 60, true, "/image/zkpvt-banner_image.png");
	public static final Product PROD5 = new Product(5, "ZK JSP", new Date(time
			- 4 * DAY + 3 * HOUR), 99.89F, 71, true, "/image/zkjsp-banner_image.png");
	public static final Product PROD6 = new Product(6, "ZATS",
			new Date(time - 4 * DAY + 3 * HOUR), 399.89F, 59, true,
			"/image/zats_banner_image.png");

	static {
		dbModel.put(PROD1.getId(), PROD1);
		dbModel.put(PROD2.getId(), PROD2);
		dbModel.put(PROD3.getId(), PROD3);
		dbModel.put(PROD4.getId(), PROD4);
		dbModel.put(PROD5.getId(), PROD5);
		dbModel.put(PROD6.getId(), PROD6);
	}

	public List<Product> findAll() {
		return new ArrayList<Product>(dbModel.values());
	}

	public List<Product> findAllAvailable() {
		ArrayList<Product> result = new ArrayList<Product>();
		for (Product prod : new ArrayList<Product>(dbModel.values())) {
			if (prod.isAvailable()) {
				result.add(prod);
			}
		}
		return result;
	}

	public Product remove(long productId) {
		Product prod = dbModel.get(productId);
		prod.setAvailable(false);
		return prod;
	}

	public Product putOn(long productId) {
		Product prod = dbModel.get(productId);
		prod.setAvailable(true);
		return prod;
	}

}
