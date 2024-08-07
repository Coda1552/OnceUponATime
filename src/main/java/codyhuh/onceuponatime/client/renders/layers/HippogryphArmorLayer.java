package codyhuh.onceuponatime.client.renders.layers;

import codyhuh.onceuponatime.client.models.HippogryphModel;
import codyhuh.onceuponatime.common.entities.Hippogryph;
import codyhuh.onceuponatime.common.items.DyeableHippogryphArmorItem;
import codyhuh.onceuponatime.common.items.HippogryphArmorItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HippogryphArmorLayer extends RenderLayer<Hippogryph, HippogryphModel<Hippogryph>> {
   private final HippogryphModel<Hippogryph> model;

   public HippogryphArmorLayer(RenderLayerParent<Hippogryph, HippogryphModel<Hippogryph>> pRenderer, EntityModelSet pModelSet) {
      super(pRenderer);
      this.model = new HippogryphModel<>(pModelSet.bakeLayer(HippogryphModel.LAYER_LOCATION));
   }

   public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Hippogryph pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      ItemStack itemstack = pLivingEntity.getArmor();
      if (itemstack.getItem() instanceof HippogryphArmorItem armorItem) {
         this.getParentModel().copyPropertiesTo(this.model);
         this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
         this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
         float f;
         float f1;
         float f2;
         if (armorItem instanceof DyeableHippogryphArmorItem dyeableArmorItem) {
            int i = (dyeableArmorItem).getColor(itemstack);
            f = (float)(i >> 16 & 255) / 255.0F;
            f1 = (float)(i >> 8 & 255) / 255.0F;
            f2 = (float)(i & 255) / 255.0F;
         } else {
            f = 1.0F;
            f1 = 1.0F;
            f2 = 1.0F;
         }

         VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(armorItem.getTexture()));
         this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
      }
   }
}