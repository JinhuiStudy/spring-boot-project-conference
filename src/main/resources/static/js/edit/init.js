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
      preloader : 'https://gifimage.net/wp-content/uploads/2018/04/loading-icon-gif-6.gif',
      resources: ['vimeo'],
      per_page: 5,
      youtubeLoadCallback: () => 'hey!',
      vimeoLoadUrl: '/today/vimeo/data',
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


function getHtml(){
  console.log(editor.getHtml());
}

function getJs(){
  console.log(editor.getJs());
}

function getCss(){
  console.log(editor.getCss());
}

function save() {
  const data = editor.getProjectData();
  console.log(data);
  editor.Storage.store(data);
}

