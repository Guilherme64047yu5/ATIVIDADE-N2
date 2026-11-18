const $=id=>document.getElementById(id);let livros=[],categorias=[],usuarios=[];
async function api(url,opts={}){const r=await fetch('/api'+url,{headers:{'Content-Type':'application/json'},...opts});if(r.status===401){location.href='/login.html';throw new Error('SessÃ£o expirada.');}if(!r.ok){let d={};try{d=await r.json()}catch{}throw new Error(d.erro||'Erro na operaÃ§Ã£o.');}return r.status===204?null:r.json();}
function feedback(m){$('feedback').textContent=m;setTimeout(()=>$('feedback').textContent='',3500)}
function actions(type,id){return `<button onclick="editar${type}(${id})">Editar</button> <button class="secondary" onclick="excluir${type}(${id})">Excluir</button>`}
async function carregar(){try{const me=await api('/auth/me');$('usuario').textContent='OlÃ¡, '+me.nome;[livros,categorias,usuarios]=await Promise.all([api('/livros'),api('/categorias'),api('/usuarios')]);render();atualizarOpcoesVenda();}catch(e){feedback(e.message)}}
function render(){ $('listaLivros').innerHTML=livros.map(l=>`<tr><td>${l.titulo}</td><td>${l.autor}</td><td>${l.isbn}</td><td>R$ ${Number(l.preco).toFixed(2)}</td><td>${l.estoque??0}</td><td>${actions('Livro',l.id)}</td></tr>`).join('');$('listaCategorias').innerHTML=categorias.map(c=>`<tr><td>${c.nome}</td><td>${c.descricao||''}</td><td>${c.departamento||''}</td><td>${c.ativa?'Sim':'NÃ£o'}</td><td>${actions('Categoria',c.id)}</td></tr>`).join('');$('listaUsuarios').innerHTML=usuarios.map(u=>`<tr><td>${u.nome}</td><td>${u.email}</td><td>${u.perfil}</td><td>${actions('Usuario',u.id)}</td></tr>`).join('');$('categoriaLivro').innerHTML='<option value="">Sem categoria</option>'+categorias.map(c=>`<option value="${c.id}">${c.nome}</option>`).join('');}
function abrir(id){$(id).classList.remove('hidden')}function fechar(id){$(id).classList.add('hidden')}
document.querySelectorAll('.tab').forEach(b=>b.onclick=()=>{document.querySelectorAll('.tab').forEach(x=>x.classList.remove('active'));b.classList.add('active');['livros','categorias','usuarios','vendas'].forEach(x=>$(x).classList.toggle('hidden',x!==b.dataset.tab));if(b.dataset.tab==='vendas')carregarHistorico();});
$('novoLivro').onclick=()=>{$('formLivro').reset();$('livroId').value='';abrir('formLivro')};$('cancelarLivro').onclick=()=>fechar('formLivro');
$('formLivro').onsubmit=async e=>{e.preventDefault();const id=$('livroId').value;const d={titulo:$('titulo').value,autor:$('autor').value,isbn:$('isbn').value,preco:Number($('preco').value),estoque:Number($('estoque').value),categoria:$('categoriaLivro').value?{id:Number($('categoriaLivro').value)}:null};try{await api('/livros'+(id?'/'+id:''),{method:id?'PUT':'POST',body:JSON.stringify(d)});fechar('formLivro');await carregar();feedback('Livro salvo com sucesso.')}catch(e){feedback(e.message)}};
window.editarLivro=id=>{const l=livros.find(x=>x.id===id);$('livroId').value=id;$('titulo').value=l.titulo;$('autor').value=l.autor;$('isbn').value=l.isbn;$('preco').value=l.preco;$('estoque').value=l.estoque;$('categoriaLivro').value=l.categoria?.id||'';abrir('formLivro')};
window.excluirLivro=async id=>{if(confirm('Excluir este livro?'))try{await api('/livros/'+id,{method:'DELETE'});await carregar();feedback('Livro excluÃ­do.')}catch(e){feedback(e.message)}};
$('novaCategoria').onclick=()=>{$('formCategoria').reset();$('categoriaId').value='';abrir('formCategoria')};$('cancelarCategoria').onclick=()=>fechar('formCategoria');
$('formCategoria').onsubmit=async e=>{e.preventDefault();const id=$('categoriaId').value,d={nome:$('catNome').value,descricao:$('catDescricao').value,departamento:$('catDepartamento').value,ativa:$('catAtiva').value==='true'};try{await api('/categorias'+(id?'/'+id:''),{method:id?'PUT':'POST',body:JSON.stringify(d)});fechar('formCategoria');await carregar();feedback('Categoria salva.')}catch(e){feedback(e.message)}};
window.editarCategoria=id=>{const c=categorias.find(x=>x.id===id);$('categoriaId').value=id;$('catNome').value=c.nome;$('catDescricao').value=c.descricao||'';$('catDepartamento').value=c.departamento||'';$('catAtiva').value=String(c.ativa);abrir('formCategoria')};
window.excluirCategoria=async id=>{if(confirm('Excluir esta categoria?'))try{await api('/categorias/'+id,{method:'DELETE'});await carregar();feedback('Categoria excluÃ­da.')}catch(e){feedback(e.message)}};
$('novoUsuario').onclick=()=>{$('formUsuario').reset();$('usuarioId').value='';abrir('formUsuario')};$('cancelarUsuario').onclick=()=>fechar('formUsuario');
$('formUsuario').onsubmit=async e=>{e.preventDefault();const id=$('usuarioId').value,d={nome:$('userNome').value,email:$('userEmail').value,senha:$('userSenha').value};try{await api('/usuarios'+(id?'/'+id:''),{method:id?'PUT':'POST',body:JSON.stringify(d)});fechar('formUsuario');await carregar();feedback('UsuÃ¡rio salvo.')}catch(e){feedback(e.message)}};
window.editarUsuario=id=>{const u=usuarios.find(x=>x.id===id);$('usuarioId').value=id;$('userNome').value=u.nome;$('userEmail').value=u.email;$('userSenha').required=false;$('userSenha').value='';abrir('formUsuario')};
window.excluirUsuario=async id=>{if(confirm('Excluir este usuÃ¡rio?'))try{await api('/usuarios/'+id,{method:'DELETE'});await carregar();feedback('UsuÃ¡rio excluÃ­do.')}catch(e){feedback(e.message)}};
$('sair').onclick=async()=>{await api('/auth/logout',{method:'POST'});location.href='/login.html'};carregar();

let carrinho=[];

function atualizarOpcoesVenda(){
    const select=$('vendaLivro');
    if(!select)return;
    select.innerHTML='<option value="">Selecione um livro</option>'+
        livros.filter(l=>Number(l.estoque)>0).map(l=>
            `<option value="${l.id}">${l.titulo} - R$ ${Number(l.preco).toFixed(2)} (Estoque: ${l.estoque})</option>`
        ).join('');
}

function renderCarrinho(){
    $('listaCarrinho').innerHTML=carrinho.map((item,i)=>`
        <tr>
            <td>${item.titulo}</td>
            <td>${item.quantidade}</td>
            <td>R$ ${item.subtotal.toFixed(2)}</td>
            <td><button class="secondary" onclick="removerItemCarrinho(${i})">Remover</button></td>
        </tr>
    `).join('');

    const total=carrinho.reduce((soma,item)=>soma+item.subtotal,0);
    $('totalVenda').textContent=total.toFixed(2).replace('.',',');
}

window.removerItemCarrinho=function(indice){
    carrinho.splice(indice,1);
    renderCarrinho();
};

$('adicionarCarrinho').onclick=()=>{
    const id=Number($('vendaLivro').value);
    const quantidade=Number($('vendaQuantidade').value);
    const livro=livros.find(l=>l.id===id);

    if(!livro){
        feedback('Selecione um livro.');
        return;
    }

    if(!Number.isInteger(quantidade)||quantidade<=0){
        feedback('Informe uma quantidade válida.');
        return;
    }

    const existente=carrinho.find(item=>item.livroId===id);
    const quantidadeAtual=existente?existente.quantidade:0;

    if(quantidadeAtual+quantidade>Number(livro.estoque)){
        feedback('Quantidade maior que o estoque disponível.');
        return;
    }

    if(existente){
        existente.quantidade+=quantidade;
        existente.subtotal=existente.quantidade*Number(livro.preco);
    }else{
        carrinho.push({
            livroId:id,
            titulo:livro.titulo,
            quantidade:quantidade,
            subtotal:quantidade*Number(livro.preco)
        });
    }

    renderCarrinho();
    $('vendaQuantidade').value=1;
};

$('finalizarVenda').onclick=async()=>{
    if(carrinho.length===0){
        feedback('Adicione livros ao carrinho.');
        return;
    }

    try{
        const resultado=await api('/vendas',{
            method:'POST',
            body:JSON.stringify({
                produtos:carrinho.map(item=>({
                    livroId:item.livroId,
                    quantidade:item.quantidade
                }))
            })
        });

        carrinho=[];
        renderCarrinho();
        await carregar();
        await carregarHistorico();
        feedback(resultado.mensagem);
    }catch(e){
        feedback(e.message);
    }
};

async function carregarHistorico(){
    try{
        const vendas=await api('/vendas');
        $('historicoVendas').innerHTML=vendas.map(v=>`
            <tr>
                <td>${v.id}</td>
                <td>${new Date(v.data).toLocaleString('pt-BR')}</td>
                <td>R$ ${Number(v.total).toFixed(2)}</td>
            </tr>
        `).join('');
    }catch(e){
        feedback(e.message);
    }
}


