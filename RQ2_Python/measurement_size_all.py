import matplotlib.pyplot as plt
from matplotlib.ticker import PercentFormatter

label = [5, 10, 15, 20, 25]

combination = [0.233, 0.179, 0.141, 0.116, 0.086]
ciRatio = [0.172, 0.137, 0.120, 0.104, 0.094]

plt.gca().yaxis.set_major_formatter(PercentFormatter(1, 0))
plt.plot(label, combination, label="Wilcoxon + Cliff's Delta effect size", color="orange")
plt.plot(label, ciRatio, label="CI ratio", linestyle="--", color="green")
plt.ylabel('significant different distribution detected [% benchmarks]')
plt.xlabel('# iterations')
plt.ylim(0, 0.3)
plt.xticks(label)
plt.legend()
plt.tight_layout()
#plt.show()
plt.savefig('C:\\Users\\stewue\\OneDrive - Wuersten\\Uni\\19_HS\\Masterarbeit\\Repo\\Evaluation\\RQ2_Results\\images\\measurement_size_all_forks.pdf')

