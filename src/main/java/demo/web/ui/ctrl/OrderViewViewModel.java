package demo.web.ui.ctrl;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.South;

import demo.model.DAOs;
import demo.model.bean.Order;

public class OrderViewViewModel  {
	
//	@Wire
//	private Div orderArea;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		System.out.println("in aftercompose");
		System.out.println(view.getDefinition().getName());
        Selectors.wireComponents(view, this, false);
    }
	
	private Order selectedItem;

	public Order getSelectedItem() {
		return selectedItem;
	}
	
	@NotifyChange("selectedItem")
	public void setSelectedItem(Order selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public List<Order> getOrders() {
		List<Order> orders = DAOs.getOrderDAO().findByUser(UserUtils.getCurrentUserId());
		return orders;
	}
	
	@Command
	@NotifyChange({"orders", "selectedItem"})
	public void cancelOrder() {
		if (getSelectedItem() == null) {
			return;
		}
		
		DAOs.getOrderDAO().cancelOrder(getSelectedItem().getId());
		setSelectedItem(null);
	}
	
	@GlobalCommand
	@NotifyChange("orders")
	public void updateOrders() {
		//no post processing needed
	}
	
	@Command
	public void expandView(){
		System.out.println("success");
//		System.out.println(orderArea.getId());
//		South s = (South)orderArea.getParent();
//		s.setSize("100%");
	}
}
