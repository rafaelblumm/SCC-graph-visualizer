import os
import subprocess
import streamlit as st

DEFAULT_GRAPH_WIDTH = 500
GRAPH_WIDTH_CHANGE = 30

GRAPH_SAMPLES = [
    "0 -> [ 1 ]\n1 -> [ 2 7 ]\n2 -> [ 3 6 ]\n3 -> [ 4 ]\n4 -> [ 2 5 ]\n6 -> [ 3 5 ]\n7 -> [ 0 6 ]",
    "0 -> [ 1 5 ]\n2 -> [ 0 3 ]\n3 -> [ 2 5 ]\n4 -> [ 2 3 ]\n5 -> [ 4 ]\n6 -> [ 0 4 9 ]\n" +
    "7 -> [ 6 8 ]\n8 -> [ 7 9 ]\n9 -> [ 10 11 ]\n10 -> [ 12 ]\n11 -> [ 4 12 ]\n12 -> [ 9 ]",
    "0 -> [ 1 ]\n1 -> [ 2 ]\n2 -> [ 3 4 ]\n3 -> [ 0 ]\n4 -> [ 2 ]"
]

st.set_page_config(
    page_title="SCC visualizer",
    page_icon=":computer:",
    layout="wide",
    initial_sidebar_state="expanded"
)

if "graph_width" not in st.session_state:
    st.session_state["graph_width"] = DEFAULT_GRAPH_WIDTH

if "graph_text" not in st.session_state:
    st.session_state["graph_text"] = ""

with st.sidebar:
    col_1, col_2, col_3 = st.columns(3)
    with col_1:
        if st.button(":heavy_minus_sign:", use_container_width=True):
            st.session_state["graph_width"] -= GRAPH_WIDTH_CHANGE

    with col_2:
        if st.button("Reset", use_container_width=True):
            st.session_state["graph_width"] = DEFAULT_GRAPH_WIDTH

    with col_3:
        if st.button(":heavy_plus_sign:", use_container_width=True):
            st.session_state["graph_width"] += GRAPH_WIDTH_CHANGE

    st.divider()

    sample_columns = st.columns(3)
    for i in range(0, 3):
        with sample_columns[i]:
            if st.button(f"Sample {i + 1}", use_container_width=True):
                st.session_state["graph_text"] = GRAPH_SAMPLES[i]

    graph_text_input = st.text_area(
        "Insira o grafo",
        st.session_state["graph_text"],
        height=450
    )

    if st.button("Gerar", type="primary"):
        graph_param = ";".join(graph_text_input.split("\n"))
        cmd = ["java", "-jar", "/app/SCCGraphGenerator.jar", graph_param]
        print("CMD=" + " ".join(cmd))

        stderr = subprocess.run(cmd, stderr=subprocess.PIPE).stderr.decode("utf-8").strip()
        print(stderr)
        if len(stderr) != 0:
            st.error(stderr)
    
    st.divider()
    st.caption("Estruturas Avançadas de Dados II")
    st.caption("Rafael Blumm")

st.subheader("Visualizador de :green[componentes fortemente conectados] de um grafo", divider="gray")
graph_svg = "/app/tmp/generated_graph.svg"
if os.path.isfile(graph_svg):
    st.image(graph_svg, width=st.session_state["graph_width"])
