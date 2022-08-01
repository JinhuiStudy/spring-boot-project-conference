var editor = grapesjs.init({
  height: '100%',
  showOffsets: 1,
  noticeOnUnload: 0,
  container: '#gjs',
  fromElement: true,
  panels: panels,
  storageManager: storageManager,

  // gjs-blocks-basic: https://github.com/artf/grapesjs-blocks-basic
  // grapesjs-tabs: https://github.com/artf/grapesjs-tabs
  // grapesjs-style-bg: https://github.com/artf/grapesjs-style-bg
  plugins: ['gjs-blocks-basic', 'grapesjs-tabs', 'grapesjs-style-bg', 'grapesjs-video-embed-manager'],
  pluginsOpts: {
    'grapesjs-video-embed-manager': {
      memberIdx: memberIdx,
      preloader : 'https://gifimage.net/wp-content/uploads/2018/04/loading-icon-gif-6.gif',
      resources: ['vimeo'],
      per_page: 5,
      youtubeLoadCallback: () => 'hey!',
      vimeoLoadUrl: '/today/vimeo/data/' + memberIdx,
      displayLoadElement: document.getElementById('loader'),
      displayLayoutElement: document.getElementById('layout_body')
    },
  },
  i18n: {
    locale: 'ko',
    localeFallback: 'ko',
    messages: {
      ko
    }
  },
  assetManager: assetManager,
  styleManager: styleManager,
});

// editor.BlockManager.add(
//     'image', {
//       label: 'Image',
//       category: 'Basic',
//       attributes: { class: 'gjs-fonts gjs-f-image' },
//       content: {
//         style: { color: 'black' },
//         type: 'image',
//         activeOnRender: 1
//       }
//     }
// );


