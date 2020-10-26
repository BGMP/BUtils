package cl.bgmp.butils.entity;

import javax.annotation.Nonnull;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkBuilder {
  private int power = 1;
  private FireworkEffect.Type type = FireworkEffect.Type.BALL;
  private Color[] color = null;
  private Color[] fade = null;
  private boolean flicker = false;
  private boolean trail = false;

  public FireworkBuilder() {}

  public FireworkBuilder setPower(int power) {
    this.power = power;
    return this;
  }

  public FireworkBuilder setType(FireworkEffect.Type type) {
    this.type = type;
    return this;
  }

  public FireworkBuilder setColor(Color... color) {
    this.color = color;
    return this;
  }

  public FireworkBuilder setFade(Color... color) {
    this.fade = color;
    return this;
  }

  public FireworkBuilder flicker() {
    this.flicker = true;
    return this;
  }

  public FireworkBuilder trail() {
    this.trail = true;
    return this;
  }

  public void spawn(@Nonnull Location location) {
    Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
    FireworkMeta fireworkMeta = firework.getFireworkMeta();

    fireworkMeta.setPower(this.power);

    FireworkEffect.Builder effectBuilder = FireworkEffect.builder();
    effectBuilder
        .with(this.type)
        .withColor(this.color)
        .flicker(this.flicker)
        .trail(this.trail)
        .build();
    if (this.color != null) effectBuilder.withColor(this.color);
    if (this.fade != null) effectBuilder.withFade(this.fade);

    fireworkMeta.addEffect(effectBuilder.build());
    firework.setFireworkMeta(fireworkMeta);
  }
}
