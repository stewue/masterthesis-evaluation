import matplotlib.pyplot as plt
import numpy as np
from matplotlib.ticker import PercentFormatter

cov = [0.1, 0.2, 0.3, 0.33, 0.5, 0.6, 0.7]
ci = [0.2, 0.3, 0.31, 0.35, 0.52, 0.62, 0.72]
divergence = [0.15, 0.22, 0.27, 0.3, 0.47, 0.55, 0.88]

valuesCov, baseCov = np.histogram(cov, bins=1000, range=[0, 1], weights=np.ones(len(cov)) / len(cov))
cumulativeCov = np.cumsum(valuesCov)
plt.plot(baseCov[:-1], cumulativeCov, label="COV")

valuesCi, baseCi = np.histogram(ci, bins=100, range=[0, 1], weights=np.ones(len(ci)) / len(ci))
cumulativeCi = np.cumsum(valuesCi)
plt.plot(baseCi[:-1], cumulativeCi, label="CI")

valuesDivergence, baseDivergence = np.histogram(divergence, bins=100, range=[0, 1], weights=np.ones(len(divergence)) / len(divergence))
cumulativeDivergence = np.cumsum(valuesDivergence)
plt.plot(baseDivergence[:-1], cumulativeDivergence, label="Divergence")

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.gca().xaxis.set_major_formatter(PercentFormatter(1))
plt.legend()
plt.xlabel('Time used compared to standard execution')
plt.xlim(-0.01, 1.01)
plt.ylim(-0.01, 1.01)
plt.ylabel('cumulative probability')
plt.tight_layout()
#plt.show()
plt.savefig('export.png')
