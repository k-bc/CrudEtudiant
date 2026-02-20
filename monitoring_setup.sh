#!/bin/bash
# Monitoring Setup - Prometheus & Grafana
# Pour khaled@khaled-VirtualBox
# Usage: bash monitoring_setup.sh <DOCKER_NETWORK>
# Example: bash monitoring_setup.sh testpipeline_default

if [ -z "$1" ]; then
    echo "Usage: bash $0 <DOCKER_NETWORK>"
    echo "Example: bash $0 testpipeline_default"
    exit 1
fi

DOCKER_NETWORK=$1
MONITORING_DIR=~/monitoring

echo "==========================================="
echo "Monitoring Setup"
echo "Network: $DOCKER_NETWORK"
echo "==========================================="

# STEP 1: Créer le répertoire
echo ""
echo "[STEP 1] Création répertoire $MONITORING_DIR"
mkdir -p $MONITORING_DIR
cd $MONITORING_DIR
echo "✓ Répertoire créé"

# STEP 2: Créer prometheus.yml
echo ""
echo "[STEP 2] Création prometheus.yml"
cat > $MONITORING_DIR/prometheus.yml << 'EOF'
global:
  scrape_interval: 10s

scrape_configs:

  - job_name: 'springboot-app'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['testpipeline-springboot-app-1:8089']
EOF
echo "✓ prometheus.yml créé"

# STEP 3: Lancer Prometheus
echo ""
echo "[STEP 3] Lancement Prometheus"
docker run -d \
  --name prometheus \
  --network $DOCKER_NETWORK \
  --restart unless-stopped \
  -p 9090:9090 \
  -v $MONITORING_DIR/prometheus.yml:/etc/prometheus/prometheus.yml \
  prom/prometheus
sleep 2
echo "✓ Prometheus démarré (port 9090)"

# STEP 4: Lancer Grafana
echo ""
echo "[STEP 4] Lancement Grafana"
docker run -d \
  --name grafana \
  --network $DOCKER_NETWORK \
  --restart unless-stopped \
  -p 3000:3000 \
  grafana/grafana
sleep 2
echo "✓ Grafana démarré (port 3000)"

# Vérification
echo ""
echo "==========================================="
echo "Vérification"
echo "==========================================="
docker ps | grep -E "prometheus|grafana"

echo ""
echo "==========================================="
echo "✓ MONITORING SETUP TERMINÉ"
echo "==========================================="
echo ""
echo "Accès:"
echo "  Prometheus: http://IP_VM:9090"
echo "  Grafana: http://IP_VM:3000 (admin/admin)"
echo ""
echo "Prochaines étapes:"
echo "  1. Vérifier http://IP_VM:9090 → Status → Targets"
echo "  2. Accéder http://IP_VM:3000"
echo "  3. Ajouter data source: http://prometheus:9090"
echo "  4. Importer dashboard ID 4701"
echo ""

