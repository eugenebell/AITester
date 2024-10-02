# AITester

Uses Ollama as AI engine

Get started here https://github.com/ollama/ollama

To start it run
Show model information
ollama show llama3.1
List models on your computer
ollama list
List which models are currently loaded
ollama ps
Stop a model which is currently running
ollama stop llama3.1
Start Ollama
ollama serve - is used when you want to start ollama without running the desktop application.

Running local builds
Next, start the server:

./ollama serve
Finally, in a separate shell, run a model:

./ollama run llama3.1

Test using curl:
curl http://localhost:11434/api/generate -d "{\"model\": \"llama3.1\",  \"prompt\":\"Why is the sky blue?\"}"


Examples for Ollama and add huggingface models
https://medium.com/@sudarshan-koirala/ollama-huggingface-8e8bc55ce572

Other interesting models
coding - https://ollama.com/library/stable-code

