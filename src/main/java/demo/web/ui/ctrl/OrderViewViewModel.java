package demo.web.ui.ctrl;

import java.io.IOException;
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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.North;

import demo.model.DAOs;
import demo.model.bean.Order;

public class OrderViewViewModel  {
	
	private Order selectedItem;

	@Wire
	private Div orderArea;
	
	private North n;
	
	
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, false);
        n = (North)orderArea.getParent();
    }
	
	
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
		//method exists for refreshing the UI only, ie. VM notifies Binder, Binder updates the View
	}
	
	@GlobalCommand
	public void openOrderView(){
		n.setOpen(true);
	}
	
	@Command
	public void closeOrderView(){
		n.setOpen(false);
	}
}
