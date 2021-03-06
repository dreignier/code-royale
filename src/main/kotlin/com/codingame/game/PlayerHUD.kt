package com.codingame.game

import com.codingame.game.Constants.QUEEN_HP
import com.codingame.game.Constants.QUEEN_HP_MULT

class PlayerHUD(private val player: Player, isSecondPlayer: Boolean) {
  private val left = if (isSecondPlayer) 1920/2 else 0
  private val right = if (isSecondPlayer) 1920 else 1920/2
  private val top = viewportY.last
  private val bottom = 1080

  private val healthBarWidth = 420

  private val avatar = theEntityManager.createSprite()
    .setImage(player.avatarToken)
    .setY(bottom).setAnchorY(1.0)
    .apply { if (isSecondPlayer) { x = 1920; anchorX = 1.0 } else { x = 0 } }
    .setBaseWidth(140)
    .setBaseHeight(140)
    .setZIndex(4003)!!

  init {
    val maskCircle = theEntityManager.createCircle()
      .setRadius(64)
      .setX( if (isSecondPlayer) 1920-69 else 69)
      .setY(bottom - 68)

    avatar.setMask(maskCircle)
  }

  private val healthBarFillMask = theEntityManager.createRectangle()!!
    .setLineAlpha(0.0)
    .setY(top + 39)
    .setX(if (isSecondPlayer) 1920 - 139 - healthBarWidth else 143)
    .setWidth(healthBarWidth).setHeight(28)
    .setFillColor(player.colorToken)
    .setFillAlpha(0.0)
    .setLineWidth(0)
    .setZIndex(4002)

  private val healthBarFill = theEntityManager.createSprite()
    .setImage(if (isSecondPlayer) "Life-Bleu" else "Life-Rouge")
    .setX(if (isSecondPlayer) 1920 - 139 - healthBarWidth else 143)
    .setY(top + 39)
    .setZIndex(4002)
    .setMask(healthBarFillMask)

  private val playerName = theEntityManager.createText(player.nicknameToken)!!
    .setY(bottom - 45).setAnchorY(1.0)
    .apply { if (isSecondPlayer) { x = 1770; anchorX = 1.0 } else { x = 150 }}
    .setFillColor(0xffffff)
    .setScale(1.8)
    .setFontFamily("Arial Black")
    .setZIndex(4003)

  private val healthText = theEntityManager.createText(player.health.toString())!!
    .setX(healthBarFillMask.x + healthBarFillMask.width - 10).setY(healthBarFillMask.y + healthBarFillMask.height/2)
    .setAnchorX(1.0).setAnchorY(0.5)
    .setScale(1.3)
    .setFontFamily("Arial Black")
    .setFillColor(0xffffff)
    .setZIndex(4003)

  private val moneyText = theEntityManager.createText("0")
    .setY(bottom - 40).setAnchorY(0.5)
    .setX(if (isSecondPlayer) 1020 else 700)
    .setFillColor(0xffffff)
    .setScale(1.8)
    .setFontFamily("Arial Black")
    .setZIndex(4002)!!

  private val moneyIncText = theEntityManager.createText("")
    .setY(bottom - 40).setAnchorY(0.5)
    .setX(if (isSecondPlayer) 1220 else 910).setAnchorX(1.0)
    .setFillColor(0xffffff)
    .setScale(1.2)
    .setFontFamily("Arial Black")
    .setZIndex(4002)!!

  fun update() {
    healthBarFillMask.width = healthBarWidth * player.health / (QUEEN_HP.last * QUEEN_HP_MULT)
    healthText.text = player.health.toString()
    moneyText.text = player.gold.toString()
    moneyIncText.text = when (player.goldPerTurn) {
      0 -> ""
      else -> "+${player.goldPerTurn}"
    }
    theEntityManager.commitEntityState(0.0, moneyText, moneyIncText)
  }

  companion object {
    lateinit var obstacles: List<Obstacle>
  }
}