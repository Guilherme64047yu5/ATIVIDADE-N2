const formulario = document.getElementById('cadastroForm');

formulario.addEventListener('submit', async (evento) => {
    evento.preventDefault();

    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const mensagem = document.getElementById('mensagem');

    try {
        const resposta = await fetch('/api/auth/cadastro', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nome, email, senha })
        });

        const dados = await resposta.json();

        if (!resposta.ok) {
            mensagem.textContent = dados.erro || dados.mensagem || dados.message || 'Não foi possível realizar o cadastro.';
            return;
        }

        mensagem.textContent = 'Cadastro realizado com sucesso!';
        formulario.reset();

    } catch (erro) {
        mensagem.textContent = 'Erro ao conectar com o servidor.';
    }
});

