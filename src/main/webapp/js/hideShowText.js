const span = document.querySelectorAll('#content')

span.forEach(function showhide(s) {
    const text = s.querySelector("h3")
    const oldText = text.innerText;
    const newText = text.innerText.substring(0,120);

    if (text.innerText.length > 120){
        text.innerText =newText;

        const button = document.createElement('button')
        button.innerText = '...'
        text.appendChild(button)
        button.addEventListener('click', function () {
            console.log(oldText);
            text.innerText = oldText;
            text.appendChild(button)
        })
    } else {
        const button = document.createElement('button')
        button.innerText = 'pokaz mniej'
        text.appendChild(button)
        button.addEventListener('click', function () {
            console.log(oldText);
            text.innerText = newText;
            text.appendChild(button)
        })
    }
})
