var styleManager = {
    sectors: [
        {
            name: 'General',
            open: false,
            properties: ['display', 'float', 'position', 'top', 'right', 'left', 'bottom'],
        },
        {
            name: 'Flex',
            open: false,
            properties: [
                'flex-direction',
                'flex-wrap',
                'justify-content',
                'align-items',
                'align-content',
                'order',
                'flex-basis',
                'flex-grow',
                'flex-shrink',
                'align-self',
            ],
        },
        {
            name: 'Dimension',
            open: false,
            properties: ['width', 'height', 'max-width', 'min-height', 'margin', 'padding'],
        },
        {
            name: 'Typography',
            open: false,
            properties: [
                'font-family',
                'font-size',
                'font-weight',
                'letter-spacing',
                'color',
                'line-height',
                'text-align',
                'text-shadow',
            ],
        },
        {
            name: 'Decorations',
            open: false,
            properties: ['background-color', 'border-radius', 'border', 'box-shadow', 'background'],
        },
    ],

    // Specify the element to use as a container, string (query) or HTMLElement
    // With the empty value, nothing will be rendered
    appendTo: '',

    // Style prefix
    stylePrefix: 'sm-',

    // Avoid rendering the default style manager.
    custom: false,
};
