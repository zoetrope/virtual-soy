
function App() {
    var state = mercury.struct({
        value: mercury.value(0),
        handles: mercury.value(null)
    });

    state.handles.set(mercury.handles({
        clicks: incrementCounter
    }, state));

    return state;
}

App.render = function render(state) {
    return mercury.h('div.counter', [
        'The state ', mercury.h('code', 'clickCount'),
        ' has value: ' + state.value + '.', mercury.h('input.button', {
            type: 'button',
            value: 'Click me!',
            'ev-click': mercury.event(state.handles.clicks)
        })
    ]);
};

function incrementCounter(state) {
    state.value.set(state.value() + 1);
}

mercury.app(document.getElementById('content'), App(), App.render);
