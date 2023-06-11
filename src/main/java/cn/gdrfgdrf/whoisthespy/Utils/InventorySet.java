package cn.gdrfgdrf.whoisthespy.Utils;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventorySet {
    private ChestGui chestGui;
    private OutlinePane outlinePane;
    private PaginatedPane paginatedPane;
    private StaticPane staticPane;
}
