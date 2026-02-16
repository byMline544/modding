package tcw.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import tcw.TCWCreativeTab;

public class TCWItem extends Item {

    protected final String textureKey;

    public TCWItem(int id, String textureKey) {
        super(id);
        this.textureKey = textureKey;
        setUnlocalizedName(textureKey);
        setCreativeTab(TCWCreativeTab.TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon("technocloud:" + textureKey);
    }
}
