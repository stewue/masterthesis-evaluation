import matplotlib.pyplot as plt
from matplotlib.ticker import PercentFormatter

label = [5,10,15,20,25]

# single fork
# combination = [0.233, 0.179, 0.141, 0.116, 0.086]
# ciRatio = [0.172, 0.137, 0.120, 0.104, 0.094]

# all forks
combination = [0.143, 0.096, 0.068, 0.053, 0.040]
ciRatio = [0.135, 0.121, 0.105, 0.103, 0.086]

plt.gca().yaxis.set_major_formatter(PercentFormatter(1))
plt.plot(label, combination, label="Wilcoxon + effect size")
plt.plot(label, ciRatio, label="CI ratio")
plt.ylabel('# benchmarks where a significant change is detected')
plt.xlabel('# iterations')
plt.ylim(0, 0.3)
plt.xticks(label)
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')

