package br.com.DesafioSpring.Receitas.principal;

import br.com.DesafioSpring.Receitas.model.Receita;
import br.com.DesafioSpring.Receitas.service.ConsumoApi;
import br.com.DesafioSpring.Receitas.service.ConverteDados;
import br.com.DesafioSpring.Receitas.model.Response;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://www.themealdb.com/api/json/v1/1/";

    public  void exibeMenu(){
        var menu = """
                Digite a opção desejada:
                1 - Buscas receita por nome
                2 - Listar receitar por letra
                3 - Buscar uma receita aleatória
                4 - Sair
                """;

        System.out.println(menu);
        var opcao = scanner.nextLine();

        if(opcao.equals("1")){
            buscaPorNome();
        } else if (opcao.equals("2")) {
            listaPorLetra();
        } else if (opcao.equals("3")) {
            buscaAleatoria();
        } else if (opcao.equals("4")) {
            System.out.println("saindo");
            return;
        }else {
            System.out.println("Opção inválida. Tente novamente");
        }


    }

    private void buscaPorNome() {
        while (true){
            try {
                System.out.println("Digite o nome da receita:");
                String nome = scanner.nextLine();
                String endereco = URL_BASE + "search.php?s=" + nome;

                String json = consumo.obterDados(endereco);
                Response response = conversor.obterDados(json, Response.class);
                List<Receita> receitas = List.of(response.meals);


                List<Receita> receitasFiltradas = receitas.stream()
                        .filter(receita -> receita.nome().toLowerCase().contains(nome.toLowerCase()))
                        .collect(Collectors.toList());

                if (receitasFiltradas.isEmpty()) {
                    System.out.println("Nenhuma receita encontrada com o nome: " + nome);
                } else {
                    exibirReceitas(receitasFiltradas); // Exibe as receitas filtradas
                }
                break;

            }catch (IllegalArgumentException ex){
                System.out.println("Erro" + ex.getMessage());
                System.out.println("Tente novamente.");
            }catch (Exception ex){
                System.out.println("Ocorreu um erro na busca da receita: " + ex.getMessage());
                System.out.println("Tente novamente.");
            }
        }
    }

    private void listaPorLetra() {
            while (true) {
                try {
                    System.out.println("Digite a letra inicial:");
                    char letra = scanner.nextLine().charAt(0);
                    String endereco = URL_BASE + "search.php?f=" + letra;

                    String json = consumo.obterDados(endereco);
                    Response response = conversor.obterDados(json, Response.class);
                    List<Receita> receitas = List.of(response.meals);

                    List<Receita> receitasFiltradas = receitas.stream()
                            .filter(receita -> receita.nome().toLowerCase().startsWith(String.valueOf(letra).toLowerCase()))
                            .collect(Collectors.toList());

                    if (receitasFiltradas.isEmpty()) {
                        System.out.println("Nenhuma receita encontrada que comece com a letra: " + letra);
                    } else {
                        exibirReceitas(receitasFiltradas);
                    }
                    break;

                } catch (Exception e) {
                    System.out.println("Ocorreu um erro ao listar as receitas: " + e.getMessage());
                    System.out.println("Tente novamente.");
                }
            }
        }

    private void buscaAleatoria() {
        String endereco = URL_BASE + "random.php";

        String json = consumo.obterDados(endereco);
        Response response = conversor.obterDados(json, Response.class);
        List<Receita> receitas = List.of(response.meals);

        exibirReceitas(receitas);
    }

    private void exibirReceitas(List<Receita> receitas) {
        if (receitas.isEmpty()) {
            System.out.println("Nenhuma receita encontrada.");
        } else {
            for (Receita receita : receitas) {
                System.out.println("ID: " + receita.id());
                System.out.println("Nome: " + receita.nome());
                System.out.println("Categoria: " + receita.categoria());
                System.out.println("Área: " + receita.area());
                System.out.println("Instruções: " + receita.instrucoes());
                System.out.println("-----------------------------");
            }
        }
    }
}
