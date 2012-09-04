package demo.web.ui.ctrl;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zk.ui.metainfo.Parser;

import demo.model.DAOs;
import demo.model.bean.Product;
import demo.web.OverQuantityException;

/**
 * @author zkessentials store
 * 
 *         This is the controller for the product view as referenced in
 *         index.zul
 * 
 */
public class ProductViewCtrl extends SelectorComposer<Div> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4327599559929787819L;

	@Wire
	private Grid prodGrid;

	@Override
	public void doAfterCompose(Div comp) throws Exception {
		super.doAfterCompose(comp);

		List<Product> prods = DAOs.getProductDAO().findAllAvailable();

		ListModelList<Product> prodModel = new ListModelList<Product>(prods);
		prodGrid.setModel(prodModel);
		
		
		prodGrid.setRowRenderer(new RowRenderer<Product>(){

			public void render(Row arg0, Product arg1, int arg2)
					throws Exception {
				Image img = new Image(arg1.getImgPath());
				img.setHeight("70px");
				img.setWidth("70px");
				img.setParent(arg0);
				new Label(arg1.getName()).setParent(arg0);
				new Label(String.valueOf(arg1.getPrice())).setParent(arg0);
				new Label(String.valueOf(arg1.getQuantity())).setParent(arg0);
				new Label(arg1.getCreateDate().toString()).setParent(arg0);
				ProductOrder po = new ProductOrder(arg1.getQuantity(),arg1);
				po.afterCompose();
				po.setParent(arg0);
			}
		});
		
	
		
		
	}
	
	@Listen("onAddProductOrder=#PrdoDiv #prodGrid row productOrder") //use cell(since productOrder extends cell) instead of productOrder if component name (productOrder) was not specified in component directive  
	public void addProduct(Event fe) {

		if (!(fe.getTarget() instanceof ProductOrder)) {
			return;
		}

		ProductOrder po = (ProductOrder) fe.getTarget();

		try {
			UserUtils.getShoppingCart()
					.add(po.getProduct(), po.getQuantity());
		} catch (OverQuantityException e) {
			po.setError(e.getMessage());
		}

		BindUtils.postGlobalCommand(null, null, "updateShoppingCart", null);
	}
}
