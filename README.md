# Cocktail Mod (Minecraft 1.21.1 NeoForge)

A fun and lightweight Minecraft mod that introduces craftable cocktails with unique status effects!

---

## 🍉 Features

### 1. Melon Cocktail
* **Recipe**: Shapeless crafting in any 2×2 or 3×3 grid:
  * `1x Melon Slice` (`minecraft:melon_slice`)
  * `1x Glass Bottle` (`minecraft:glass_bottle`)
* **Behavior**:
  * **Drinkable**: Plays drinking sounds and animation (`UseAnim.DRINK`).
  * **Empty Bottle Return**: Consuming the cocktail returns an empty glass bottle (`minecraft:glass_bottle`) to your hand or inventory.
  * **Always Drinkable**: Can be enjoyed even if your hunger bar is completely full.
* **Status Effect**:
  * Gives **15 seconds of Nausea / Dizziness** (`MobEffects.CONFUSION` / `minecraft:nausea`), causing the camera and screen to wobble in a disorienting, drunken swirl with potion particles.
  * Restores `4` nutrition and `0.3` saturation.
* **Stack Size**: Stacks up to `16` (standard for potions and drinks).
* **Creative Tab**: Found under the custom **Cocktails** tab and the vanilla **Food & Drinks** tab.

---

## 🛠️ Building from Source

### Prerequisites
* Java 21+ JDK (tested with Java 21 & GraalVM 23)

### Build the Jar
```bash
./gradlew build
```
The compiled jar will be generated at:
```
build/libs/cocktailmod-1.0.0.jar
```

### Run Directly in Dev Client
```bash
./gradlew runClient
```

---

## 🎮 How to Install & Play in CurseForge

1. Open **CurseForge**.
2. Click **Create Custom Profile** (`+` button).
3. Set:
   * **Profile Name**: `Cocktail Mod Test`
   * **Minecraft Version**: `1.21.1`
   * **Game Type**: `NeoForge`
4. Click **Create**.
5. Once created, right-click the profile (or click `...`) and select **Open Folder**.
6. Place `cocktailmod-1.0.0.jar` into the `mods/` directory.
7. Click **Play** and enjoy crafting your cocktails!
