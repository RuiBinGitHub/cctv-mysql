
/**
 * @time: 2018/11/139:47 AM
 * @author:QingMings(1821063757@qq.com)
 * @desc: CesiumLocal 本地化Cesium
 *
 */
 class CesiumLocal {
  /**
   * @time: 2018/11/139:48 AM
   * @author:QingMings(1821063757@qq.com)
   * @desc: CesiumLocal 本地化Cesium
   * @param viewer {Cesium.Viewer}
   */
  constructor(viewer) {
    this._viewer = viewer;
    this._lang = {
      "Imagery": "影像",
      "Terrain": "地形",
      "GeocodersPlaceholder": "请输入地名或坐标",
      "HomeButtonToolTip": "回到全球",
      "SceneModePickerToolTip2D": "二维",
      "SceneModePickerToolTip3D": "三维",
      "SceneModePickerToolTipView": "哥伦布视图",
      "NavigationHelpButtonToolTip": "操作指南",
      "navigationHelp_Touch": ">手势",
      "navigationHelp_Mouse": ">鼠标",
      "navigationHelp_Mouse_Pan": "平移",
      "navigationHelp_Mouse_Pan_details": "按下左键+ 拖动",
      "navigationHelp_Mouse_Zoom": "缩放",
      "navigationHelp_Mouse_Zoom_details0": "按下右键+ 拖动,或者",
      "navigationHelp_Mouse_Zoom_details1": "滚动鼠标滚轮",
      "navigationHelp_Mouse_Rotate": "旋转",
      "navigationHelp_Mouse_Rotate_details0": "按下中键+ 拖动,或者",
      "navigationHelp_Mouse_Rotate_details1": "按下CTRL + 左键/右键 +拖动",

      "navigationHelp_Touch_Pan": "平移",
      "navigationHelp_Touch_Pan_details": "单指拖动",
      "navigationHelp_Touch_Zoom": "缩放",
      "navigationHelp_Touch_Zoom_details": "双指捏合",
      "navigationHelp_Touch_Rotate": "旋转",
      "navigationHelp_Touch_Rotate_details": "双指反向拖动",
      "navigationHelp_Touch_Tilt": "俯仰",
      "navigationHelp_Touch_Tilt_details": "双指同向拖动",

      "enterFullScreen": "全屏",
      "exitFullScreen": "退出全屏"
    };
    this.init();
  }

  /**
   * @time: 2018/11/139:59 AM
   * @author:QingMings(1821063757@qq.com)
   * @desc:
   * @type Viewer
   */
  get viewer() {
    return this._viewer;
  }

  /**
   * @time: 2018/11/1310:04 AM
   * @author:QingMings(1821063757@qq.com)
   * @desc:
   * @type Object
   */
  get lang() {
    return this._lang;
  }

  init() {
    let _vm = this;
    _vm._baseLayerPickerSectionTitleLang();
    _vm._homeButtonLang();
    _vm._geocoderLang();
    _vm._sceneModePickerButtonLang();
    _vm._navigationHelpButtonLang();
    _vm._fullScreenButtonLang();
  }

  /**
   * @time: 2018/11/139:55 AM
   * @author:QingMings(1821063757@qq.com)
   * @desc: 本地化 Cesium.BaseLayerPicker
   *
   */
  _baseLayerPickerSectionTitleLang() {
    let _vm = this;
    let titles = _vm.viewer.container.getElementsByClassName('cesium-baseLayerPicker-sectionTitle');
    for (let i = 0; i < titles.length; i++) {
      titles[i].innerHTML = titles[i].innerHTML.replace("Imagery", _vm.lang.Imagery).replace("Terrain", _vm.lang.Terrain)
    }
  }

  /**
   * @time: 2018/11/1310:17 AM
   * @author:QingMings(1821063757@qq.com)
   * @desc: 本地化 HomeButton
   *
   */
  _homeButtonLang() {
    let _vm = this;
    if (!defined(_vm.viewer.homeButton)) {
      return;
    }
    _vm.viewer.homeButton.viewModel.tooltip = _vm.lang.HomeButtonToolTip
  }

  /**
   * @time: 2018/11/1310:20 AM
   * @author:QingMings(1821063757@qq.com)
   * @desc: 本地化 geocoder
   *
   */
  _geocoderLang() {
    let _vm = this;
    let geocoders = this.viewer.container.getElementsByClassName("cesium-geocoder-input");
    for (let i = 0; i < geocoders.length; i++) {
      geocoders[i].setAttribute("placeholder", _vm.lang.GeocodersPlaceholder);
    }
  }

  /**
   * @time: 2018/11/1311:22 AM
   * @author:QingMings(1821063757@qq.com)
   * @desc: 本地化 sceneModePicker
   *
   */
  _sceneModePickerButtonLang() {
    let _vm = this;
    if (!defined(_vm.viewer.sceneModePicker)) {
      return;
    }
    _vm.viewer.sceneModePicker.viewModel.tooltip2D = _vm.lang.SceneModePickerToolTip2D;
    _vm.viewer.sceneModePicker.viewModel.tooltip3D = _vm.lang.SceneModePickerToolTip3D;
    _vm.viewer.sceneModePicker.viewModel.tooltipColumbusView = _vm.lang.SceneModePickerToolTipView;
  }

  /**
   * @time: 2018/11/1311:23 AM
   * @author:QingMings(1821063757@qq.com)
   * @desc: 本地化操作指南
   *
   */
  _navigationHelpButtonLang() {
    let _vm = this;
    if (!defined(_vm.viewer.navigationHelpButton)) {
      return;
    }
    _vm.viewer.navigationHelpButton.viewModel.tooltip = _vm.lang.NavigationHelpButtonToolTip;
    let clickHelpElement = _vm.viewer.navigationHelpButton.container.getElementsByClassName("cesium-click-navigation-help")[0];
    let touchHelpElement = _vm.viewer.navigationHelpButton.container.getElementsByClassName("cesium-touch-navigation-help")[0];

    let tabButtonElement = _vm.viewer.navigationHelpButton.container.getElementsByClassName("cesium-navigation-button-right")[0];
    tabButtonElement.innerHTML = tabButtonElement.innerHTML.replace(">Touch", _vm.lang.navigationHelp_Touch);
    tabButtonElement = _vm.viewer.navigationHelpButton.container.getElementsByClassName("cesium-navigation-button-left")[0];
    tabButtonElement.innerHTML = tabButtonElement.innerHTML.replace(">Mouse", _vm.lang.navigationHelp_Mouse);
    // 平移
    let clickHelpPanElement = clickHelpElement.getElementsByClassName("cesium-navigation-help-pan")[0];
    clickHelpPanElement.innerHTML = _vm.lang.navigationHelp_Mouse_Pan;
    let clickHelpPanDetailsElement = clickHelpPanElement.parentNode.getElementsByClassName("cesium-navigation-help-details")[0];
    clickHelpPanDetailsElement.innerHTML = _vm.lang.navigationHelp_Mouse_Pan_details;
    // 缩放
    let clickHelpZoomElement = clickHelpElement.getElementsByClassName("cesium-navigation-help-zoom")[0];
    clickHelpZoomElement.innerHTML = _vm.lang.navigationHelp_Mouse_Zoom;
    let clickHelpZoomDetailsElement0 = clickHelpZoomElement.parentNode.getElementsByClassName("cesium-navigation-help-details")[0];
    clickHelpZoomDetailsElement0.innerHTML = _vm.lang.navigationHelp_Mouse_Zoom_details0;
    let clickHelpZoomDetailsElement1 = clickHelpZoomElement.parentNode.getElementsByClassName("cesium-navigation-help-details")[1];
    clickHelpZoomDetailsElement1.innerHTML = _vm.lang.navigationHelp_Mouse_Zoom_details1;
    // 旋转
    let clickHelpRotateElement = clickHelpElement.getElementsByClassName("cesium-navigation-help-rotate")[0];
    clickHelpRotateElement.innerHTML = _vm.lang.navigationHelp_Mouse_Rotate;
    let clickHelpRotateDetailsElement0 = clickHelpRotateElement.parentNode.getElementsByClassName("cesium-navigation-help-details")[0];
    clickHelpRotateDetailsElement0.innerHTML = _vm.lang.navigationHelp_Mouse_Rotate_details0;
    let clickHelpRotateDetailsElement1 = clickHelpRotateElement.parentNode.getElementsByClassName("cesium-navigation-help-details")[1];
    clickHelpRotateDetailsElement1.innerHTML = _vm.lang.navigationHelp_Mouse_Rotate_details1;

    let touchHelpPanElement = touchHelpElement.getElementsByClassName("cesium-navigation-help-pan")[0];
    touchHelpPanElement.innerHTML = _vm.lang.navigationHelp_Touch_Pan;
    let touchHelpPanDetailsElement = touchHelpPanElement.parentNode.getElementsByClassName("cesium-navigation-help-details")[0];
    touchHelpPanDetailsElement.innerHTML = _vm.lang.navigationHelp_Touch_Pan_details;

    let touchHelpZoomElement = touchHelpElement.getElementsByClassName("cesium-navigation-help-zoom")[0];
    touchHelpZoomElement.innerHTML = _vm.lang.navigationHelp_Touch_Zoom;
    let touchHelpZoomDetailsElement = touchHelpZoomElement.parentNode.getElementsByClassName("cesium-navigation-help-details")[0];
    touchHelpZoomDetailsElement.innerHTML = _vm.lang.navigationHelp_Touch_Zoom_details;

    let touchHelpTiltElement = touchHelpElement.getElementsByClassName("cesium-navigation-help-rotate")[0];
    touchHelpTiltElement.innerHTML = _vm.lang.navigationHelp_Touch_Tilt;
    let touchHelpTiltDetailsElement = touchHelpTiltElement.parentNode.getElementsByClassName("cesium-navigation-help-details")[0];
    touchHelpTiltDetailsElement.innerHTML = _vm.lang.navigationHelp_Touch_Tilt_details;

    let touchHelpRotateElement = touchHelpElement.getElementsByClassName("cesium-navigation-help-tilt")[0];
    touchHelpRotateElement.innerHTML = _vm.lang.navigationHelp_Touch_Rotate;
    let touchHelpRotateDetailsElement = touchHelpRotateElement.parentNode.getElementsByClassName("cesium-navigation-help-details")[0];
    touchHelpRotateDetailsElement.innerHTML = _vm.lang.navigationHelp_Touch_Rotate_details;
  }

  /**
   * @time: 2018/11/133:31 PM
   * @author:QingMings(1821063757@qq.com)
   * @desc: 本地化全屏按钮
   *
   */
  _fullScreenButtonLang() {
    let _vm = this;
    if (!defined(_vm.viewer.fullscreenButton)) {
      return;
    }
    let tmpIsFullscreen= Cesium.knockout.getObservable(_vm.viewer.fullscreenButton.viewModel,"isFullscreen");
    delete _vm.viewer.fullscreenButton.viewModel.tooltip;
    Cesium.knockout.defineProperty(_vm.viewer.fullscreenButton.viewModel,"tooltip",function () {
      if (!_vm.viewer.fullscreenButton.viewModel.isFullscreenEnabled){
        return 'Full screen unavailable';
      }
      return tmpIsFullscreen() ? _vm.lang.exitFullScreen : _vm.lang.enterFullScreen;
    });
    // 重新绑定全屏按钮
    let fullScreenButtonElement = _vm.viewer.fullscreenButton.container.getElementsByClassName("cesium-fullscreenButton")[0];
    Cesium.knockout.cleanNode(fullScreenButtonElement);
    Cesium.knockout.applyBindings(_vm.viewer.fullscreenButton.viewModel,fullScreenButtonElement);
  }
}