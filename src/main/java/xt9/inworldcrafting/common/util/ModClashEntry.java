package xt9.inworldcrafting.common.util;

import net.minecraftforge.fml.common.Loader;

/**
 * Created by xt9 on 2019-02-03.
 */
@SuppressWarnings("WeakerAccess")
public class ModClashEntry {
    private String modid;
    private Object ingredient;
    private String description;

    public ModClashEntry(String modid, Object ingredient, String description) {
        this.modid = modid;
        this.ingredient = ingredient;
        this.description = description;
    }

    public String getModid() {
        return modid;
    }

    public Object getIngredient() {
        return ingredient;
    }

    public String getDescription() {
        return description;
    }

    public boolean isModLoaded() {
        return Loader.isModLoaded(this.modid);
    }


}
