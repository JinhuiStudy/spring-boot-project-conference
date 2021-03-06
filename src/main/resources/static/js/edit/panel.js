const swv = 'sw-visibility';
const expt = 'export-template';
const osm = 'open-sm';
const otm = 'open-tm';
const ola = 'open-layers';
const obl = 'open-blocks';
const ful = 'fullscreen';
const prv = 'preview';
const sav = 'save';

var panels = {
    stylePrefix: 'pn-',

    defaults: [
        {
            id: 'commands',
            buttons: [{

            }],
        },
        {
            id: 'options',
            buttons: [
                {
                    active: true,
                    id: swv,
                    className: 'fa fa-square-o',
                    command: swv,
                    context: swv,
                    attributes: { title: 'View components' },
                },
                {
                    id: prv,
                    className: 'fa fa-eye',
                    command: prv,
                    context: prv,
                    attributes: { title: 'Preview' },
                },
                {
                    id: ful,
                    className: 'fa fa-arrows-alt',
                    command: ful,
                    context: ful,
                    attributes: { title: 'Fullscreen' },
                },
                {
                    id: sav,
                    className: 'fa fa-floppy-o',
                    command: (editor => {
                        editor.Storage.store(editor.getProjectData())
                            .then(result => {
                                $.ajax({
                                    url: `/today/${projectID}`,
                                    type: 'POST',
                                    contentType: 'application/json',
                                    data: JSON.stringify({
                                        cssData: editor.getCss(),
                                        jsData: editor.getJs(),
                                        htmlData: editor.getHtml(),
                                    }),
                                    dataType: 'text',
                                    beforeSend: function(jqXHR) {}, // ?????? ?????? ??? ?????? ?????? ?????? return false; ??? ?????? ?????? ??????
                                    success: function(data, statusText, jqXHR) {
                                        console.log(data);
                                        console.log(statusText);
                                        console.log(jqXHR);

                                        alert("????????? ?????????????????????.");
                                    },
                                    error: function(data, statusText, jqXHR) {
                                        console.log(data);
                                        console.log(statusText);
                                        console.log(jqXHR);

                                        alert("????????? ??????????????????.");
                                    }, // ?????? ??????.
                                    complete: function(jqXHR) {} // ????????? ??????, ????????? ?????? ?????? ?????? ??? ?????? ??????
                                });
                            })
                            .catch(error => {
                                console.log(error);
                                alert("????????? ??????????????????.")
                            });
                    }),
                    context: sav,
                    attributes: { title: 'Save' },
                },
                // {
                //     id: expt,
                //     className: 'fa fa-code',
                //     command: expt,
                //     attributes: { title: 'View code' },
                // },
            ],
        },
        {
            id: 'views',
            buttons: [
                {
                    id: osm,
                    className: 'fa fa-paint-brush',
                    command: osm,
                    active: true,
                    togglable: 0,
                    attributes: { title: 'Open Style Manager' },
                },
                {
                    id: otm,
                    className: 'fa fa-cog',
                    command: otm,
                    togglable: 0,
                    attributes: { title: 'Settings' },
                },
                {
                    id: ola,
                    className: 'fa fa-bars',
                    command: ola,
                    togglable: 0,
                    attributes: { title: 'Open Layer Manager' },
                },
                {
                    id: obl,
                    className: 'fa fa-th-large',
                    command: obl,
                    togglable: 0,
                    attributes: { title: 'Open Blocks' },
                },
            ],
        },
    ],

    // Editor model
    em: null,

    // Delay before show children buttons (in milliseconds)
    delayBtnsShow: 300,
};
