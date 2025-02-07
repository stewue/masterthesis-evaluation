import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd

data = {'x': [
        'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A',
        'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A', 'benchmark A',
        'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B',
        'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B', 'benchmark B'
],
        'Type': [
            "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations",
            "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations",
            "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations", "performance over the first n iterations",
            "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations", "performance over the first n + 1 iterations",
        ],
        'y' : [
            100, 90, 80, 95, 110, 105, 103, 97, 100, 110,
            70, 85, 75, 90, 105, 100, 97, 92, 95, 85,
            100, 90, 80, 95, 110, 105, 103, 97, 100, 110,
            95, 90, 80, 95, 110, 105, 103, 97, 105, 110 ]}

df = pd.DataFrame(data)

ax = sns.violinplot(x="x", y="y", hue="Type", data=df, split=True)
ax.axes.get_yaxis().set_ticks([])
ax.set_xlabel('')
plt.legend(loc='lower right')
plt.ylabel('invocation time')
plt.tight_layout()
#plt.show()
plt.savefig('export.pdf')
