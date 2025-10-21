# 📱 جميع أكواد التصميم لتطبيق CodeAssist

## 📋 فهرس المحتويات
1. [ملفات الألوان](#colors)
2. [ملفات الأنماط والثيمات](#styles)
3. [ملفات التخطيطات الرئيسية](#layouts)
4. [ملفات الأبعاد](#dimensions)
5. [ملفات Drawable](#drawables)

---

<a name="colors"></a>
## 🎨 1. ملفات الألوان (Colors Files)

### 📄 values/colors.xml (الألوان المعدلة العصرية)
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="abc_decor_view_status_guard">#ff000000</color>
    <color name="abc_decor_view_status_guard_light">#ffffffff</color>
    <color name="abc_search_url_text_normal">#ff7fa87f</color>
    <color name="abc_search_url_text_pressed">@android:color/black</color>
    <color name="abc_search_url_text_selected">@android:color/black</color>
    <color name="accent_material_dark">@color/material_deep_teal_200</color>
    <color name="accent_material_light">@color/material_deep_teal_500</color>
    <color name="androidx_core_ripple_material_light">#1f000000</color>
    <color name="androidx_core_secondary_text_default_material_light">#8a000000</color>
    <color name="background_floating_material_dark">@color/material_grey_800</color>
    <color name="background_floating_material_light">@android:color/white</color>
    <color name="background_material_dark">@color/material_grey_850</color>
    <color name="background_material_light">@color/material_grey_50</color>
    <color name="black">#00000000</color>
    <color name="bright_foreground_disabled_material_dark">#80ffffff</color>
    <color name="bright_foreground_disabled_material_light">#80000000</color>
    <color name="bright_foreground_inverse_material_dark">@color/bright_foreground_material_light</color>
    <color name="bright_foreground_inverse_material_light">@color/bright_foreground_material_dark</color>
    <color name="bright_foreground_material_dark">@android:color/white</color>
    <color name="bright_foreground_material_light">@android:color/black</color>
    <color name="button_material_dark">#ff5a595b</color>
    <color name="button_material_light">#ffd6d7d7</color>
    <color name="cardview_dark_background">#ff424242</color>
    <color name="cardview_light_background">#ffffffff</color>
    <color name="cardview_shadow_end_color">#03000000</color>
    <color name="cardview_shadow_start_color">#37000000</color>
    <color name="colorAccent">#ff6366f1</color>
    <color name="colorBackground">#ff0f172a</color>
    <color name="colorControlNormal">#fff1f5f9</color>
    <color name="colorHeader">@color/colorPrimary</color>
    <color name="colorOnSecondary">@android:color/white</color>
    <color name="colorPrimary">#ff6366f1</color>
    <color name="colorPrimaryDark">#ff4338ca</color>
    <color name="colorSecondary">#ff8b5cf6</color>
    <color name="colorSurface">#ff1e293b</color>
    <color name="design_bottom_navigation_shadow_color">#14000000</color>
    <color name="design_dark_default_color_background">#ff121212</color>
    <color name="design_dark_default_color_error">#ffcf6679</color>
    <color name="design_dark_default_color_on_background">#ffffffff</color>
    <color name="design_dark_default_color_on_error">#ff000000</color>
    <color name="design_dark_default_color_on_primary">#ff000000</color>
    <color name="design_dark_default_color_on_secondary">#ff000000</color>
    <color name="design_dark_default_color_on_surface">#ffffffff</color>
    <color name="design_dark_default_color_primary">#ffba86fc</color>
    <color name="design_dark_default_color_primary_dark">#ff000000</color>
    <color name="design_dark_default_color_primary_variant">#ff3700b3</color>
    <color name="design_dark_default_color_secondary">#ff03dac6</color>
    <color name="design_dark_default_color_secondary_variant">#ff03dac6</color>
    <color name="design_dark_default_color_surface">#ff121212</color>
    <color name="design_default_color_background">#ffffffff</color>
    <color name="design_default_color_error">#ffb00020</color>
    <color name="design_default_color_on_background">#ff000000</color>
    <color name="design_default_color_on_error">#ffffffff</color>
    <color name="design_default_color_on_primary">#ffffffff</color>
    <color name="design_default_color_on_secondary">#ff000000</color>
    <color name="design_default_color_on_surface">#ff000000</color>
    <color name="design_default_color_primary">#ff6200ee</color>
    <color name="design_default_color_primary_dark">#ff3700b3</color>
    <color name="design_default_color_primary_variant">#ff3700b3</color>
    <color name="design_default_color_secondary">#ff03dac6</color>
    <color name="design_default_color_secondary_variant">#ff018786</color>
    <color name="design_default_color_surface">#ffffffff</color>
    <color name="design_fab_shadow_end_color">@android:color/transparent</color>
    <color name="design_fab_shadow_mid_color">#14000000</color>
    <color name="design_fab_shadow_start_color">#44000000</color>
    <color name="design_fab_stroke_end_inner_color">#0a000000</color>
    <color name="design_fab_stroke_end_outer_color">#0f000000</color>
    <color name="design_fab_stroke_top_inner_color">#1affffff</color>
    <color name="design_fab_stroke_top_outer_color">#2effffff</color>
    <color name="design_snackbar_background_color">#ff323232</color>
    <color name="dim_foreground_disabled_material_dark">#80bebebe</color>
    <color name="dim_foreground_disabled_material_light">#80323232</color>
    <color name="dim_foreground_material_dark">#ffbebebe</color>
    <color name="dim_foreground_material_light">#ff323232</color>
    <color name="error_color_material_dark">#ffff7043</color>
    <color name="error_color_material_light">#ffff5722</color>
    <color name="foreground_material_dark">@android:color/white</color>
    <color name="foreground_material_light">@android:color/black</color>
    <color name="highlighted_text_material_dark">#6680cbc4</color>
    <color name="highlighted_text_material_light">#66009688</color>
    <color name="m3_ref_palette_black">#ff000000</color>
    <color name="m3_ref_palette_error0">#ff000000</color>
    <color name="m3_ref_palette_error10">#ff410e0b</color>
    <color name="m3_ref_palette_error100">#ffffffff</color>
    <color name="m3_ref_palette_error20">#ff601410</color>
    <color name="m3_ref_palette_error30">#ff8c1d18</color>
    <color name="m3_ref_palette_error40">#ffb3261e</color>
    <color name="m3_ref_palette_error50">#ffdc362e</color>
    <color name="m3_ref_palette_error60">#ffe46962</color>
    <color name="m3_ref_palette_error70">#ffec928e</color>
    <color name="m3_ref_palette_error80">#fff2b8b5</color>
    <color name="m3_ref_palette_error90">#fff9dedc</color>
    <color name="m3_ref_palette_error95">#fffceeee</color>
    <color name="m3_ref_palette_error99">#fffffbf9</color>
    <color name="m3_ref_palette_neutral0">#ff000000</color>
    <color name="m3_ref_palette_neutral10">#ff1c1b1f</color>
    <color name="m3_ref_palette_neutral100">#ffffffff</color>
    <color name="m3_ref_palette_neutral20">#ff313033</color>
    <color name="m3_ref_palette_neutral30">#ff484649</color>
    <color name="m3_ref_palette_neutral40">#ff605d62</color>
    <color name="m3_ref_palette_neutral50">#ff787579</color>
    <color name="m3_ref_palette_neutral60">#ff939094</color>
    <color name="m3_ref_palette_neutral70">#ffaeaaae</color>
    <color name="m3_ref_palette_neutral80">#ffc9c5ca</color>
    <color name="m3_ref_palette_neutral90">#ffe6e1e5</color>
    <color name="m3_ref_palette_neutral95">#fff4eff4</color>
    <color name="m3_ref_palette_neutral99">#fffffbfe</color>
    <color name="m3_ref_palette_neutral_variant0">#ff000000</color>
    <color name="m3_ref_palette_neutral_variant10">#ff1d1a22</color>
    <color name="m3_ref_palette_neutral_variant100">#ffffffff</color>
    <color name="m3_ref_palette_neutral_variant20">#ff322f37</color>
    <color name="m3_ref_palette_neutral_variant30">#ff49454f</color>
    <color name="m3_ref_palette_neutral_variant40">#ff605d66</color>
    <color name="m3_ref_palette_neutral_variant50">#ff79747e</color>
    <color name="m3_ref_palette_neutral_variant60">#ff938f99</color>
    <color name="m3_ref_palette_neutral_variant70">#ffaea9b4</color>
    <color name="m3_ref_palette_neutral_variant80">#ffcac4d0</color>
    <color name="m3_ref_palette_neutral_variant90">#ffe7e0ec</color>
    <color name="m3_ref_palette_neutral_variant95">#fff5eefa</color>
    <color name="m3_ref_palette_neutral_variant99">#fffffbfe</color>
    <color name="m3_ref_palette_primary0">#ff000000</color>
    <color name="m3_ref_palette_primary10">#ff21005d</color>
    <color name="m3_ref_palette_primary100">#ffffffff</color>
    <color name="m3_ref_palette_primary20">#ff381e72</color>
    <color name="m3_ref_palette_primary30">#ff4f378b</color>
    <color name="m3_ref_palette_primary40">#ff6750a4</color>
    <color name="m3_ref_palette_primary50">#ff7f67be</color>
    <color name="m3_ref_palette_primary60">#ff9a82db</color>
    <color name="m3_ref_palette_primary70">#ffb69df8</color>
    <color name="m3_ref_palette_primary80">#ffd0bcff</color>
    <color name="m3_ref_palette_primary90">#ffeaddff</color>
    <color name="m3_ref_palette_primary95">#fff6edff</color>
    <color name="m3_ref_palette_primary99">#fffffbfe</color>
    <color name="m3_ref_palette_secondary0">#ff000000</color>
    <color name="m3_ref_palette_secondary10">#ff1d192b</color>
    <color name="m3_ref_palette_secondary100">#ffffffff</color>
    <color name="m3_ref_palette_secondary20">#ff332d41</color>
    <color name="m3_ref_palette_secondary30">#ff4a4458</color>
    <color name="m3_ref_palette_secondary40">#ff625b71</color>
    <color name="m3_ref_palette_secondary50">#ff7a7289</color>
    <color name="m3_ref_palette_secondary60">#ff958da5</color>
    <color name="m3_ref_palette_secondary70">#ffb0a7c0</color>
    <color name="m3_ref_palette_secondary80">#ffccc2dc</color>
    <color name="m3_ref_palette_secondary90">#ffe8def8</color>
    <color name="m3_ref_palette_secondary95">#fff6edff</color>
    <color name="m3_ref_palette_secondary99">#fffffbfe</color>
    <color name="m3_ref_palette_tertiary0">#ff000000</color>
    <color name="m3_ref_palette_tertiary10">#ff31111d</color>
    <color name="m3_ref_palette_tertiary100">#ffffffff</color>
    <color name="m3_ref_palette_tertiary20">#ff492532</color>
    <color name="m3_ref_palette_tertiary30">#ff633b48</color>
    <color name="m3_ref_palette_tertiary40">#ff7d5260</color>
    <color name="m3_ref_palette_tertiary50">#ff986977</color>
    <color name="m3_ref_palette_tertiary60">#ffb58392</color>
    <color name="m3_ref_palette_tertiary70">#ffd29dac</color>
    <color name="m3_ref_palette_tertiary80">#ffefb8c8</color>
    <color name="m3_ref_palette_tertiary90">#ffffd8e4</color>
    <color name="m3_ref_palette_tertiary95">#ffffecf1</color>
    <color name="m3_ref_palette_tertiary99">#fffffbfa</color>
    <color name="m3_ref_palette_white">#ffffffff</color>
    <color name="m3_sys_color_dark_background">@color/m3_ref_palette_neutral10</color>
    <color name="m3_sys_color_dark_error">@color/m3_ref_palette_error80</color>
    <color name="m3_sys_color_dark_error_container">@color/m3_ref_palette_error30</color>
    <color name="m3_sys_color_dark_inverse_on_surface">@color/m3_ref_palette_neutral20</color>
    <color name="m3_sys_color_dark_inverse_primary">@color/m3_ref_palette_primary40</color>
    <color name="m3_sys_color_dark_inverse_surface">@color/m3_ref_palette_neutral90</color>
    <color name="m3_sys_color_dark_on_background">@color/m3_ref_palette_neutral90</color>
    <color name="m3_sys_color_dark_on_error">@color/m3_ref_palette_error20</color>
    <color name="m3_sys_color_dark_on_error_container">@color/m3_ref_palette_error90</color>
    <color name="m3_sys_color_dark_on_primary">@color/m3_ref_palette_primary20</color>
    <color name="m3_sys_color_dark_on_primary_container">@color/m3_ref_palette_primary90</color>
    <color name="m3_sys_color_dark_on_secondary">@color/m3_ref_palette_secondary20</color>
    <color name="m3_sys_color_dark_on_secondary_container">@color/m3_ref_palette_secondary90</color>
    <color name="m3_sys_color_dark_on_surface">@color/m3_ref_palette_neutral90</color>
    <color name="m3_sys_color_dark_on_surface_variant">@color/m3_ref_palette_neutral_variant80</color>
    <color name="m3_sys_color_dark_on_tertiary">@color/m3_ref_palette_tertiary20</color>
    <color name="m3_sys_color_dark_on_tertiary_container">@color/m3_ref_palette_tertiary90</color>
    <color name="m3_sys_color_dark_outline">@color/m3_ref_palette_neutral_variant60</color>
    <color name="m3_sys_color_dark_outline_variant">@color/m3_ref_palette_neutral_variant30</color>
    <color name="m3_sys_color_dark_primary">@color/m3_ref_palette_primary80</color>
    <color name="m3_sys_color_dark_primary_container">@color/m3_ref_palette_primary30</color>
    <color name="m3_sys_color_dark_secondary">@color/m3_ref_palette_secondary80</color>
    <color name="m3_sys_color_dark_secondary_container">@color/m3_ref_palette_secondary30</color>
    <color name="m3_sys_color_dark_surface">@color/m3_ref_palette_neutral10</color>
    <color name="m3_sys_color_dark_surface_variant">@color/m3_ref_palette_neutral_variant30</color>
    <color name="m3_sys_color_dark_tertiary">@color/m3_ref_palette_tertiary80</color>
    <color name="m3_sys_color_dark_tertiary_container">@color/m3_ref_palette_tertiary30</color>
    <color name="m3_sys_color_light_background">@color/m3_ref_palette_neutral99</color>
    <color name="m3_sys_color_light_error">@color/m3_ref_palette_error40</color>
    <color name="m3_sys_color_light_error_container">@color/m3_ref_palette_error90</color>
    <color name="m3_sys_color_light_inverse_on_surface">@color/m3_ref_palette_neutral95</color>
    <color name="m3_sys_color_light_inverse_primary">@color/m3_ref_palette_primary80</color>
    <color name="m3_sys_color_light_inverse_surface">@color/m3_ref_palette_neutral20</color>
    <color name="m3_sys_color_light_on_background">@color/m3_ref_palette_neutral10</color>
    <color name="m3_sys_color_light_on_error">@color/m3_ref_palette_error100</color>
    <color name="m3_sys_color_light_on_error_container">@color/m3_ref_palette_error10</color>
    <color name="m3_sys_color_light_on_primary">@color/m3_ref_palette_primary100</color>
    <color name="m3_sys_color_light_on_primary_container">@color/m3_ref_palette_primary10</color>
    <color name="m3_sys_color_light_on_secondary">@color/m3_ref_palette_secondary100</color>
    <color name="m3_sys_color_light_on_secondary_container">@color/m3_ref_palette_secondary10</color>
    <color name="m3_sys_color_light_on_surface">@color/m3_ref_palette_neutral10</color>
    <color name="m3_sys_color_light_on_surface_variant">@color/m3_ref_palette_neutral_variant30</color>
    <color name="m3_sys_color_light_on_tertiary">@color/m3_ref_palette_tertiary100</color>
    <color name="m3_sys_color_light_on_tertiary_container">@color/m3_ref_palette_tertiary10</color>
    <color name="m3_sys_color_light_outline">@color/m3_ref_palette_neutral_variant50</color>
    <color name="m3_sys_color_light_outline_variant">@color/m3_ref_palette_neutral_variant80</color>
    <color name="m3_sys_color_light_primary">@color/m3_ref_palette_primary40</color>
    <color name="m3_sys_color_light_primary_container">@color/m3_ref_palette_primary90</color>
    <color name="m3_sys_color_light_secondary">@color/m3_ref_palette_secondary40</color>
    <color name="m3_sys_color_light_secondary_container">@color/m3_ref_palette_secondary90</color>
    <color name="m3_sys_color_light_surface">@color/m3_ref_palette_neutral99</color>
    <color name="m3_sys_color_light_surface_variant">@color/m3_ref_palette_neutral_variant90</color>
    <color name="m3_sys_color_light_tertiary">@color/m3_ref_palette_tertiary40</color>
    <color name="m3_sys_color_light_tertiary_container">@color/m3_ref_palette_tertiary90</color>
    <color name="material_blue_grey_800">#ff37474f</color>
    <color name="material_blue_grey_900">#ff263238</color>
    <color name="material_blue_grey_950">#ff21272b</color>
    <color name="material_deep_teal_200">#ff80cbc4</color>
    <color name="material_deep_teal_500">#ff008577</color>
    <color name="material_grey_100">#fff5f5f5</color>
    <color name="material_grey_300">#ffe0e0e0</color>
    <color name="material_grey_50">#fffafafa</color>
    <color name="material_grey_600">#ff757575</color>
    <color name="material_grey_800">#ff424242</color>
    <color name="material_grey_850">#ff303030</color>
    <color name="material_grey_900">#ff212121</color>
    <color name="material_harmonized_color_error">#ffffffff</color>
    <color name="material_harmonized_color_error_container">#ffffffff</color>
    <color name="material_harmonized_color_on_error">#ffffffff</color>
    <color name="material_harmonized_color_on_error_container">#ffffffff</color>
    <color name="material_personalized_color_background">#ffffffff</color>
    <color name="material_personalized_color_error">#ffffffff</color>
    <color name="material_personalized_color_error_container">#ffffffff</color>
    <color name="material_personalized_color_on_background">#ffffffff</color>
    <color name="material_personalized_color_on_error">#ffffffff</color>
    <color name="material_personalized_color_on_error_container">#ffffffff</color>
    <color name="material_personalized_color_on_primary">#ffffffff</color>
    <color name="material_personalized_color_on_primary_container">#ffffffff</color>
    <color name="material_personalized_color_on_secondary">#ffffffff</color>
    <color name="material_personalized_color_on_secondary_container">#ffffffff</color>
    <color name="material_personalized_color_on_surface">#ffffffff</color>
    <color name="material_personalized_color_on_surface_inverse">#ffffffff</color>
    <color name="material_personalized_color_on_surface_variant">#ffffffff</color>
    <color name="material_personalized_color_on_tertiary">#ffffffff</color>
    <color name="material_personalized_color_on_tertiary_container">#ffffffff</color>
    <color name="material_personalized_color_primary">#ffffffff</color>
    <color name="material_personalized_color_primary_container">#ffffffff</color>
    <color name="material_personalized_color_primary_inverse">#ffffffff</color>
    <color name="material_personalized_color_secondary">#ffffffff</color>
    <color name="material_personalized_color_secondary_container">#ffffffff</color>
    <color name="material_personalized_color_surface">#ffffffff</color>
    <color name="material_personalized_color_surface_inverse">#ffffffff</color>
    <color name="material_personalized_color_surface_outline">#ffffffff</color>
    <color name="material_personalized_color_surface_variant">#ffffffff</color>
    <color name="material_personalized_color_tertiary">#ffffffff</color>
    <color name="material_personalized_color_tertiary_container">#ffffffff</color>
    <color name="md_theme_dark_background">#ff0f172a</color>
    <color name="md_theme_dark_error">#fff87171</color>
    <color name="md_theme_dark_errorContainer">#ff991b1b</color>
    <color name="md_theme_dark_inverseOnSurface">#ff0f172a</color>
    <color name="md_theme_dark_inversePrimary">#ff4338ca</color>
    <color name="md_theme_dark_inverseSurface">#ffe2e8f0</color>
    <color name="md_theme_dark_onBackground">#fff1f5f9</color>
    <color name="md_theme_dark_onError">#ff7f1d1d</color>
    <color name="md_theme_dark_onErrorContainer">#fffee2e2</color>
    <color name="md_theme_dark_onPrimary">#ff1e1b4b</color>
    <color name="md_theme_dark_onPrimaryContainer">#ffe0e7ff</color>
    <color name="md_theme_dark_onSecondary">#ff3b0764</color>
    <color name="md_theme_dark_onSecondaryContainer">#ffede9fe</color>
    <color name="md_theme_dark_onSurface">#fff1f5f9</color>
    <color name="md_theme_dark_onSurfaceVariant">#ffcbd5e1</color>
    <color name="md_theme_dark_onTertiary">#ff164e63</color>
    <color name="md_theme_dark_onTertiaryContainer">#ffcffafe</color>
    <color name="md_theme_dark_outline">#ff64748b</color>
    <color name="md_theme_dark_primary">#ff818cf8</color>
    <color name="md_theme_dark_primaryContainer">#ff4338ca</color>
    <color name="md_theme_dark_primaryInverse">#ff4338ca</color>
    <color name="md_theme_dark_secondary">#ffa78bfa</color>
    <color name="md_theme_dark_secondaryContainer">#ff6d28d9</color>
    <color name="md_theme_dark_shadow">#ff000000</color>
    <color name="md_theme_dark_surface">#ff1e293b</color>
    <color name="md_theme_dark_surfaceVariant">#ff334155</color>
    <color name="md_theme_dark_tertiary">#ff22d3ee</color>
    <color name="md_theme_dark_tertiaryContainer">#ff0891b2</color>
    <color name="md_theme_light_background">#fff8fafc</color>
    <color name="md_theme_light_error">#ffef4444</color>
    <color name="md_theme_light_errorContainer">#fffee2e2</color>
    <color name="md_theme_light_inverseOnSurface">#fff1f5f9</color>
    <color name="md_theme_light_inversePrimary">#ffa5b4fc</color>
    <color name="md_theme_light_inverseSurface">#ff1e293b</color>
    <color name="md_theme_light_onBackground">#ff0f172a</color>
    <color name="md_theme_light_onError">#ffffffff</color>
    <color name="md_theme_light_onErrorContainer">#ff7f1d1d</color>
    <color name="md_theme_light_onPrimary">#ffffffff</color>
    <color name="md_theme_light_onPrimaryContainer">#ff1e1b4b</color>
    <color name="md_theme_light_onSecondary">#ffffffff</color>
    <color name="md_theme_light_onSecondaryContainer">#ff3b0764</color>
    <color name="md_theme_light_onSurface">#ff1e293b</color>
    <color name="md_theme_light_onSurfaceVariant">#ff475569</color>
    <color name="md_theme_light_onTertiary">#ffffffff</color>
    <color name="md_theme_light_onTertiaryContainer">#ff164e63</color>
    <color name="md_theme_light_outline">#ff94a3b8</color>
    <color name="md_theme_light_primary">#ff6366f1</color>
    <color name="md_theme_light_primaryContainer">#ffe0e7ff</color>
    <color name="md_theme_light_primaryInverse">#ffa5b4fc</color>
    <color name="md_theme_light_secondary">#ff8b5cf6</color>
    <color name="md_theme_light_secondaryContainer">#ffede9fe</color>
    <color name="md_theme_light_shadow">#ff000000</color>
    <color name="md_theme_light_surface">#ffffffff</color>
    <color name="md_theme_light_surfaceVariant">#fff1f5f9</color>
    <color name="md_theme_light_tertiary">#ff06b6d4</color>
    <color name="md_theme_light_tertiaryContainer">#ffcffafe</color>
    <color name="mtrl_btn_text_color_disabled">#61000000</color>
    <color name="mtrl_btn_transparent_bg_color">#00ffffff</color>
    <color name="mtrl_scrim_color">#52000000</color>
    <color name="mtrl_textinput_default_box_stroke_color">#6b000000</color>
    <color name="mtrl_textinput_disabled_color">#1f000000</color>
    <color name="mtrl_textinput_filled_box_default_background_color">#0a000000</color>
    <color name="mtrl_textinput_focused_box_stroke_color">#00000000</color>
    <color name="mtrl_textinput_hovered_box_stroke_color">#de000000</color>
    <color name="notification_action_color_filter">@color/androidx_core_secondary_text_default_material_light</color>
    <color name="notification_icon_bg_color">#ff9e9e9e</color>
    <color name="notification_material_background_media_default_color">#ff424242</color>
    <color name="preference_fallback_accent_color">#ff008577</color>
    <color name="primary_dark_material_dark">@android:color/black</color>
    <color name="primary_dark_material_light">@color/material_grey_600</color>
    <color name="primary_material_dark">@color/material_grey_900</color>
    <color name="primary_material_light">@color/material_grey_100</color>
    <color name="primary_text_default_material_dark">#ffffffff</color>
    <color name="primary_text_default_material_light">#de000000</color>
    <color name="primary_text_disabled_material_dark">#4dffffff</color>
    <color name="primary_text_disabled_material_light">#39000000</color>
    <color name="purple_200">#ffbb86fc</color>
    <color name="purple_500">#ff6200ee</color>
    <color name="purple_700">#ff3700b3</color>
    <color name="ripple_material_dark">#33ffffff</color>
    <color name="ripple_material_light">#1f000000</color>
    <color name="secondary_text_default_material_dark">#b3ffffff</color>
    <color name="secondary_text_default_material_light">#8a000000</color>
    <color name="secondary_text_disabled_material_dark">#36ffffff</color>
    <color name="secondary_text_disabled_material_light">#24000000</color>
    <color name="switch_thumb_disabled_material_dark">#ff616161</color>
    <color name="switch_thumb_disabled_material_light">#ffbdbdbd</color>
    <color name="switch_thumb_normal_material_dark">#ffbdbdbd</color>
    <color name="switch_thumb_normal_material_light">#fff1f1f1</color>
    <color name="teal_200">#ff03dac5</color>
    <color name="teal_700">#ff018786</color>
    <color name="textActionNameColor">@android:color/tertiary_text_light</color>
    <color name="textColorPrimary">#ffffffff</color>
    <color name="textColorSecondary">#ffffffff</color>
    <color name="tooltip_background_dark">#e6616161</color>
    <color name="tooltip_background_light">#e6ffffff</color>
    <color name="white">#ffeaeaea</color>
</resources>
```

<a name="styles"></a>
## 🎭 2. ملفات الأنماط (Styles Files)

### 📄 values/styles.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="ActivityTranslucent" parent="@style/Theme.AppCompat.Light.NoActionBar">
        <item name="android:windowBackground">@android:color/transparent</item>
        <item name="android:windowNoTitle">true</item>
        <item name="android:windowIsTranslucent">true</item>
        <item name="android:windowContentOverlay">@null</item>
        <item name="android:windowAnimationStyle">@null</item>
        <item name="android:background">@android:color/transparent</item>
        <item name="android:backgroundDimEnabled">false</item>
        <item name="android:windowDisablePreview">true</item>
        <item name="android:colorBackgroundCacheHint">@null</item>
        <item name="android:windowActionBar">false</item>
        <item name="android:statusBarColor">@android:color/transparent</item>
    </style>
    <style name="AlertDialog.AppCompat" parent="@style/Base.AlertDialog.AppCompat" />
    <style name="AlertDialog.AppCompat.Light" parent="@style/Base.AlertDialog.AppCompat.Light" />
    <style name="Animation.AppCompat.Dialog" parent="@style/Base.Animation.AppCompat.Dialog" />
    <style name="Animation.AppCompat.DropDownUp" parent="@style/Base.Animation.AppCompat.DropDownUp" />
    <style name="Animation.AppCompat.Tooltip" parent="@style/Base.Animation.AppCompat.Tooltip" />
    <style name="Animation.Design.BottomSheetDialog" parent="@style/Animation.AppCompat.Dialog">
        <item name="android:windowEnterAnimation">@anim/design_bottom_sheet_slide_in</item>
        <item name="android:windowExitAnimation">@anim/design_bottom_sheet_slide_out</item>
    </style>
    <style name="Animation.Material3.BottomSheetDialog" parent="@style/Animation.AppCompat.Dialog">
        <item name="android:windowEnterAnimation">@anim/m3_bottom_sheet_slide_in</item>
        <item name="android:windowExitAnimation">@anim/m3_bottom_sheet_slide_out</item>
    </style>
    <style name="Animation.Material3.SideSheetDialog" parent="@style/Animation.AppCompat.Dialog">
        <item name="android:windowEnterAnimation">@anim/m3_side_sheet_slide_in</item>
        <item name="android:windowExitAnimation">@anim/m3_side_sheet_slide_out</item>
    </style>
    <style name="Animation.MaterialComponents.BottomSheetDialog" parent="@style/Animation.AppCompat.Dialog">
        <item name="android:windowEnterAnimation">@anim/mtrl_bottom_sheet_slide_in</item>
        <item name="android:windowExitAnimation">@anim/mtrl_bottom_sheet_slide_out</item>
    </style>
    <style name="AppThemeNew" parent="@style/Theme.Material3.Light.NoActionBar">
        <item name="android:colorBackground">@color/md_theme_light_background</item>
        <item name="android:windowTranslucentStatus">false</item>
        <item name="android:windowTranslucentNavigation">false</item>
        <item name="android:statusBarColor">@android:color/transparent</item>
        <item name="android:navigationBarColor">@android:color/transparent</item>
        <item name="android:windowLightStatusBar">true</item>
        <item name="android:windowLightNavigationBar">true</item>
        <item name="colorError">@color/md_theme_light_error</item>
        <item name="colorErrorContainer">@color/md_theme_light_errorContainer</item>
        <item name="colorOnBackground">@color/md_theme_light_onBackground</item>
        <item name="colorOnError">@color/md_theme_light_onError</item>
        <item name="colorOnErrorContainer">@color/md_theme_light_onErrorContainer</item>
        <item name="colorOnPrimary">@color/md_theme_light_onPrimary</item>
        <item name="colorOnPrimaryContainer">@color/md_theme_light_onPrimaryContainer</item>
        <item name="colorOnSecondary">@color/md_theme_light_onSecondary</item>
        <item name="colorOnSecondaryContainer">@color/md_theme_light_onSecondaryContainer</item>
        <item name="colorOnSurface">@color/md_theme_light_onSurface</item>
        <item name="colorOnSurfaceInverse">@color/md_theme_light_inverseOnSurface</item>
        <item name="colorOnSurfaceVariant">@color/md_theme_light_onSurfaceVariant</item>
        <item name="colorOnTertiary">@color/md_theme_light_onTertiary</item>
        <item name="colorOnTertiaryContainer">@color/md_theme_light_onTertiaryContainer</item>
        <item name="colorOutline">@color/md_theme_light_outline</item>
        <item name="colorPrimary">@color/md_theme_light_primary</item>
        <item name="colorPrimaryContainer">@color/md_theme_light_primaryContainer</item>
        <item name="colorPrimaryInverse">@color/md_theme_light_inversePrimary</item>
        <item name="colorSecondary">@color/md_theme_light_secondary</item>
        <item name="colorSecondaryContainer">@color/md_theme_light_secondaryContainer</item>
        <item name="colorSurface">@color/md_theme_light_surface</item>
        <item name="colorSurfaceInverse">@color/md_theme_light_inverseSurface</item>
        <item name="colorSurfaceVariant">@color/md_theme_light_surfaceVariant</item>
        <item name="colorTertiary">@color/md_theme_light_tertiary</item>
        <item name="colorTertiaryContainer">@color/md_theme_light_tertiaryContainer</item>
        <item name="dividerColor">#ff555555</item>
        <item name="editorColorScheme">@style/EditorColorSchemeLight</item>
    </style>
    <style name="Base.AlertDialog.AppCompat" parent="@android:style/Widget">
        <item name="android:layout">@layout/abc_alert_dialog_material</item>
        <item name="buttonIconDimen">@dimen/abc_alert_dialog_button_dimen</item>
        <item name="listItemLayout">@layout/select_dialog_item_material</item>
        <item name="listLayout">@layout/abc_select_dialog_material</item>
        <item name="multiChoiceItemLayout">@layout/select_dialog_multichoice_material</item>
        <item name="singleChoiceItemLayout">@layout/select_dialog_singlechoice_material</item>
    </style>
    <style name="Base.AlertDialog.AppCompat.Light" parent="@style/Base.AlertDialog.AppCompat" />
    <style name="Base.Animation.AppCompat.Dialog" parent="@android:style/Animation">
        <item name="android:windowEnterAnimation">@anim/abc_popup_enter</item>
        <item name="android:windowExitAnimation">@anim/abc_popup_exit</item>
    </style>
    <style name="Base.Animation.AppCompat.DropDownUp" parent="@android:style/Animation">
        <item name="android:windowEnterAnimation">@anim/abc_grow_fade_in_from_bottom</item>
        <item name="android:windowExitAnimation">@anim/abc_shrink_fade_out_from_bottom</item>
    </style>
    <style name="Base.Animation.AppCompat.Tooltip" parent="@android:style/Animation">
        <item name="android:windowEnterAnimation">@anim/abc_tooltip_enter</item>
        <item name="android:windowExitAnimation">@anim/abc_tooltip_exit</item>
    </style>
    <style name="Base.CardView" parent="@android:style/Widget">
        <item name="cardCornerRadius">@dimen/cardview_default_radius</item>
        <item name="cardElevation">@dimen/cardview_default_elevation</item>
        <item name="cardMaxElevation">@dimen/cardview_default_elevation</item>
        <item name="cardPreventCornerOverlap">true</item>
        <item name="cardUseCompatPadding">false</item>
    </style>
    <style name="Base.DialogWindowTitle.AppCompat" parent="@android:style/Widget">
        <item name="android:textAppearance">@style/TextAppearance.AppCompat.Title</item>
        <item name="android:maxLines">1</item>
        <item name="android:scrollHorizontally">true</item>
    </style>
    <style name="Base.DialogWindowTitleBackground.AppCompat" parent="@android:style/Widget">
        <item name="android:background">@null</item>
        <item name="android:paddingLeft">?dialogPreferredPadding</item>
        <item name="android:paddingTop">@dimen/abc_dialog_padding_top_material</item>
        <item name="android:paddingRight">?dialogPreferredPadding</item>
    </style>
    <style name="Base.MaterialAlertDialog.MaterialComponents.Title.Icon" parent="@android:style/Widget">
        <item name="android:layout_width">32.0dip</item>
        <item name="android:layout_height">32.0dip</item>
        <item name="android:src">@null</item>
        <item name="android:scaleType">fitCenter</item>
        <item name="android:contentDescription">@null</item>
        <item name="android:importantForAccessibility">no</item>
    </style>
    <style name="Base.MaterialAlertDialog.MaterialComponents.Title.Panel" parent="@android:style/Widget">
        <item name="android:paddingLeft">?dialogPreferredPadding</item>
        <item name="android:paddingTop">@dimen/abc_dialog_padding_top_material</item>
        <item name="android:paddingRight">?dialogPreferredPadding</item>
        <item name="android:layout_width">fill_parent</item>
        <item name="android:layout_height">wrap_content</item>
    </style>
    <style name="Base.MaterialAlertDialog.MaterialComponents.Title.Text" parent="@style/RtlOverlay.DialogWindowTitle.AppCompat">
        <item name="android:textAppearance">?textAppearanceSubtitle1</item>
        <item name="android:textColor">@color/material_on_surface_emphasis_high_type</item>
        <item name="android:ellipsize">end</item>
        <item name="android:layout_width">wrap_content</item>
        <item name="android:layout_height">wrap_content</item>
        <item name="android:singleLine">true</item>
    </style>
    <style name="Base.TextAppearance.AppCompat" parent="@android:style/TextAppearance.Material" />
    <style name="Base.TextAppearance.AppCompat.Body1" parent="@android:style/TextAppearance.Material.Body1" />
    <style name="Base.TextAppearance.AppCompat.Body2" parent="@android:style/TextAppearance.Material.Body2" />
    <style name="Base.TextAppearance.AppCompat.Button" parent="@android:style/TextAppearance.Material.Button" />
    <style name="Base.TextAppearance.AppCompat.Caption" parent="@android:style/TextAppearance.Material.Caption" />
    <style name="Base.TextAppearance.AppCompat.Display1" parent="@android:style/TextAppearance.Material.Display1" />
    <style name="Base.TextAppearance.AppCompat.Display2" parent="@android:style/TextAppearance.Material.Display2" />
    <style name="Base.TextAppearance.AppCompat.Display3" parent="@android:style/TextAppearance.Material.Display3" />
    <style name="Base.TextAppearance.AppCompat.Display4" parent="@android:style/TextAppearance.Material.Display4" />
    <style name="Base.TextAppearance.AppCompat.Headline" parent="@android:style/TextAppearance.Material.Headline" />
    <style name="Base.TextAppearance.AppCompat.Inverse" parent="@android:style/TextAppearance.Material.Inverse" />
    <style name="Base.TextAppearance.AppCompat.Large" parent="@android:style/TextAppearance.Material.Large" />
    <style name="Base.TextAppearance.AppCompat.Large.Inverse" parent="@android:style/TextAppearance.Material.Large.Inverse" />
    <style name="Base.TextAppearance.AppCompat.Light.Widget.PopupMenu.Large" parent="@android:style/TextAppearance.Material.Widget.PopupMenu.Large" />
    <style name="Base.TextAppearance.AppCompat.Light.Widget.PopupMenu.Small" parent="@android:style/TextAppearance.Material.Widget.PopupMenu.Small" />
    <style name="Base.TextAppearance.AppCompat.Medium" parent="@android:style/TextAppearance.Material.Medium" />
    <style name="Base.TextAppearance.AppCompat.Medium.Inverse" parent="@android:style/TextAppearance.Material.Medium.Inverse" />
    <style name="Base.TextAppearance.AppCompat.Menu" parent="@android:style/TextAppearance.Material.Menu" />
    <style name="Base.TextAppearance.AppCompat.SearchResult" parent="">
        <item name="android:textStyle">normal</item>
        <item name="android:textColor">?android:textColorPrimary</item>
        <item name="android:textColorHint">?android:textColorHint</item>
    </style>
    <style name="Base.TextAppearance.AppCompat.SearchResult.Subtitle" parent="@android:style/TextAppearance.Material.SearchResult.Subtitle" />
    <style name="Base.TextAppearance.AppCompat.SearchResult.Title" parent="@android:style/TextAppearance.Material.SearchResult.Title" />
    <style name="Base.TextAppearance.AppCompat.Small" parent="@android:style/TextAppearance.Material.Small" />
    <style name="Base.TextAppearance.AppCompat.Small.Inverse" parent="@android:style/TextAppearance.Material.Small.Inverse" />
    <style name="Base.TextAppearance.AppCompat.Subhead" parent="@android:style/TextAppearance.Material.Subhead" />
    <style name="Base.TextAppearance.AppCompat.Subhead.Inverse" parent="@style/Base.TextAppearance.AppCompat.Subhead">
        <item name="android:textColor">?android:textColorPrimaryInverse</item>
        <item name="android:textColorHighlight">?android:textColorHighlightInverse</item>
        <item name="android:textColorHint">?android:textColorHintInverse</item>
        <item name="android:textColorLink">?android:textColorLinkInverse</item>
    </style>
    <style name="Base.TextAppearance.AppCompat.Title" parent="@android:style/TextAppearance.Material.Title" />
    <style name="Base.TextAppearance.AppCompat.Title.Inverse" parent="@style/Base.TextAppearance.AppCompat.Title">
        <item name="android:textColor">?android:textColorPrimaryInverse</item>
        <item name="android:textColorHighlight">?android:textColorHighlightInverse</item>
        <item name="android:textColorHint">?android:textColorHintInverse</item>
        <item name="android:textColorLink">?android:textColorLinkInverse</item>
    </style>
    <style name="Base.TextAppearance.AppCompat.Tooltip" parent="@style/Base.TextAppearance.AppCompat">
        <item name="android:textSize">14.0sp</item>
    </style>
    <style name="Base.TextAppearance.AppCompat.Widget.ActionBar.Menu" parent="@android:style/TextAppearance.Material.Widget.ActionBar.Menu" />
    <style name="Base.TextAppearance.AppCompat.Widget.ActionBar.Subtitle" parent="@android:style/TextAppearance.Material.Widget.ActionBar.Subtitle" />
    <style name="Base.TextAppearance.AppCompat.Widget.ActionBar.Subtitle.Inverse" parent="@android:style/TextAppearance.Material.Widget.ActionBar.Subtitle.Inverse" />
    <style name="Base.TextAppearance.AppCompat.Widget.ActionBar.Title" parent="@android:style/TextAppearance.Material.Widget.ActionBar.Title" />
    <style name="Base.TextAppearance.AppCompat.Widget.ActionBar.Title.Inverse" parent="@android:style/TextAppearance.Material.Widget.ActionBar.Title.Inverse" />
    <style name="Base.TextAppearance.AppCompat.Widget.ActionMode.Subtitle" parent="@android:style/TextAppearance.Material.Widget.ActionMode.Subtitle" />
    <style name="Base.TextAppearance.AppCompat.Widget.ActionMode.Title" parent="@android:style/TextAppearance.Material.Widget.ActionMode.Title" />
    <style name="Base.TextAppearance.AppCompat.Widget.Button" parent="@android:style/TextAppearance.Material.Widget.Button" />
    <style name="Base.TextAppearance.AppCompat.Widget.Button.Borderless.Colored" parent="@android:style/TextAppearance.Material.Widget.Button.Borderless.Colored" />
    <style name="Base.TextAppearance.AppCompat.Widget.Button.Colored" parent="@android:style/TextAppearance.Material.Widget.Button.Colored" />
    <style name="Base.TextAppearance.AppCompat.Widget.Button.Inverse" parent="@android:style/TextAppearance.Material.Widget.Button.Inverse" />
    <style name="Base.TextAppearance.AppCompat.Widget.DropDownItem" parent="@android:style/TextAppearance.Small">
        <item name="android:textColor">?android:textColorPrimaryDisableOnly</item>
    </style>
    <style name="Base.TextAppearance.AppCompat.Widget.PopupMenu.Header" parent="@style/TextAppearance.AppCompat">
        <item name="android:textSize">@dimen/abc_text_size_menu_header_material</item>
        <item name="android:textColor">?android:textColorSecondary</item>
        <item name="android:fontFamily">sans-serif-medium</item>
    </style>
    <style name="Base.TextAppearance.AppCompat.Widget.PopupMenu.Large" parent="@android:style/TextAppearance.Material.Widget.PopupMenu.Large" />
    <style name="Base.TextAppearance.AppCompat.Widget.PopupMenu.Small" parent="@android:style/TextAppearance.Material.Widget.PopupMenu.Small" />
    <style name="Base.TextAppearance.AppCompat.Widget.Switch" parent="@android:style/TextAppearance.Material.Button" />
```

