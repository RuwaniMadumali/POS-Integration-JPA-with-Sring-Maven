package lk.ijse.dep.spring.jpa.pos.business.custom.impl;

import lk.ijse.dep.spring.jpa.pos.business.custom.ItemBO;
import lk.ijse.dep.spring.jpa.pos.dao.custom.ItemDAO;

import lk.ijse.dep.spring.jpa.pos.entity.Item;
import lk.ijse.dep.spring.jpa.pos.util.ItemTM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ItemBOImpl implements ItemBO {

    // Dependency Declaration
    @Autowired
    private ItemDAO itemDAO;


    @Transactional(readOnly = true)
    public String getNewItemCode() throws Exception {
        String lastItemId = itemDAO.getLastItemCode();
        if (lastItemId == null) {
            return "C001";
        } else {
            int maxId = Integer.parseInt(lastItemId.replace("I", ""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
            return id;
        }

    }

    @Transactional(readOnly = true)
    public List<ItemTM> getAllItems() throws Exception {
        List<Item> allItems = itemDAO.findAll();
        List<ItemTM> items = new ArrayList<>();
        for (Item item : allItems) {
            items.add(new ItemTM(item.getCode(), item.getDescription(), item.getQtyOnHand(),
                    item.getUnitPrice().doubleValue()));
        }
        return items;
    }

    public void saveItem(String code, String description, int qtyOnHand, double unitPrice) throws Exception {
        itemDAO.save(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));


    }

    public void deleteItem(String itemCode) throws Exception {
        itemDAO.delete(itemCode);


    }

    public void updateItem(String description, int qtyOnHand, double unitPrice, String itemCode) throws Exception {
        itemDAO.update(new Item(itemCode, description,
                BigDecimal.valueOf(unitPrice), qtyOnHand));


    }
}
