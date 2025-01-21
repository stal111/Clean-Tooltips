package net.mandalacreations.clean_tooltips

import com.google.gson.JsonParser
import com.mojang.serialization.JsonOps
import net.mandalacreations.clean_tooltips.tooltip.TooltipType
import net.mandalacreations.clean_tooltips.tooltip.config.TooltipConfig
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimplePreparableReloadListener
import net.minecraft.util.profiling.ProfilerFiller

object ConfigManager : SimplePreparableReloadListener<Map<TooltipType, TooltipConfig>>() {

    val LOCATION: ResourceLocation = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "config")

    val CONFIGS = mutableMapOf<TooltipType, TooltipConfig>()

    override fun prepare(
        resourceManager: ResourceManager,
        profiler: ProfilerFiller
    ): Map<TooltipType, TooltipConfig> = TooltipType.entries.associate { it to it.loadConfig(resourceManager) }

    fun TooltipType.loadConfig(resourceManager: ResourceManager): TooltipConfig =
        resourceManager.openAsReader(this.getLocation()).use {
            this.codec
                .parse(JsonOps.INSTANCE, JsonParser.parseReader(it))
                .getOrThrow { error -> throw IllegalStateException("Failed to load tooltip config: $error") }
        }

    fun TooltipType.getLocation(): ResourceLocation = LOCATION.withSuffix("/${this.configName}.json")

    override fun apply(
        configs: Map<TooltipType, TooltipConfig>,
        resourceManager: ResourceManager,
        profiler: ProfilerFiller
    ) {
        CONFIGS.clear()
        CONFIGS.putAll(configs)
    }
}