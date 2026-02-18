package techno.api.energy;

import java.util.ArrayDeque;
import java.util.HashSet;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import techno.Util;

/*
 * Простая энергосеть в стиле IC2: источник выдает энергию только туда, где есть потребитель,
 * по кабелям/смежным машинам, с обходом BFS и ограничением за тик.
 */
public final class EnergyNet {

    private EnergyNet() {}

    public static void tickSource(World world, TileEntity source, IEnergyProducer producer) {
        if (world == null || world.isRemote) return;

        int budget = Math.min(producer.getOutputRate(), producer.getEnergyStored());
        if (budget <= 0) return;

        for (int side = 0; side < 6 && budget > 0; side++) {
            TileEntity start = Util.getAdjacent(world, source.xCoord, source.yCoord, source.zCoord, side);
            int sent = pushThroughGraph(start, budget, source);
            if (sent > 0) {
                producer.extractEnergy(sent, false);
                budget -= sent;
            }
        }
    }

    private static int pushThroughGraph(TileEntity start, int budget, TileEntity source) {
        if (start == null || budget <= 0) return 0;

        ArrayDeque<TileEntity> q = new ArrayDeque<TileEntity>();
        HashSet<String> seen = new HashSet<String>();
        q.add(start);

        while (!q.isEmpty() && budget > 0) {
            TileEntity te = q.poll();
            String key = te.xCoord + ":" + te.yCoord + ":" + te.zCoord;
            if (!seen.add(key)) continue;

            if (te instanceof IEnergyConsumer) {
                IEnergyConsumer c = (IEnergyConsumer) te;
                if (c.wantsEnergy()) {
                    int request = Math.min(c.getDemandRate(), budget);
                    int accepted = c.receiveEnergy(request, false);
                    if (accepted > 0) return accepted;
                }
            }

            if (!(te instanceof IEnergyProducer)) {
                for (int side = 0; side < 6; side++) {
                    TileEntity n = Util.getAdjacent(te.worldObj, te.xCoord, te.yCoord, te.zCoord, side);
                    if (n == null || n == source) continue;
                    q.add(n);
                }
            }
        }

        return 0;
    }
}
